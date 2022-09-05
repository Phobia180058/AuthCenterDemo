package com.demo.auth.service.impl;

import com.demo.auth.core.CacheManager;
import com.demo.auth.entity.Role;
import com.demo.auth.entity.User;
import com.demo.auth.exception.BizException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Base64;
import java.util.List;

public class AuthServiceImplTest {

    private AuthServiceImpl authService = new AuthServiceImpl();

    @BeforeClass
    public static void init() {
        CacheManager.USER_MAP.put("user1", new User().setUserName("user1").setPassword(Base64.getEncoder().encodeToString("pass1".getBytes())));
        CacheManager.USER_MAP.put("user2", new User().setUserName("user2").setPassword(Base64.getEncoder().encodeToString("pass2".getBytes())));
        CacheManager.USER_MAP.put("user3", new User().setUserName("user3").setPassword(Base64.getEncoder().encodeToString("pass3".getBytes())));
        CacheManager.USER_MAP.put("user4", new User().setUserName("user4").setPassword(Base64.getEncoder().encodeToString("pass4".getBytes())));
    }

    @Test
    public void testCreateUser() {
        String u1 = "u1";
        String p1 = "p1";
        authService.createUser(u1, p1);
        Assert.assertThrows(BizException.class, () -> authService.createUser(u1, p1));
    }

    @Test
    public void testDeleteUser() {
        authService.deleteUser("user4");
        Assert.assertThrows(BizException.class, () -> authService.deleteUser("u1"));
    }

    @Test
    public void testAddRole() {
        authService.addRole("user1", "role1");
        Assert.assertTrue(CacheManager.USER_ROLE_MAP.get("user1").contains("role1"));
        Assert.assertThrows(BizException.class, () -> authService.addRole("user", "role1"));
    }

    @Test
    public void testAuthenticate() {
        String token = Base64.getEncoder().encodeToString(("user1" + "-" + Base64.getEncoder().encodeToString("pass1".getBytes())).getBytes());
        Assert.assertTrue(token.equals(authService.authenticate("user1", "pass1")));
        Assert.assertThrows(BizException.class, () -> authService.authenticate("u1", "p1"));
        Assert.assertThrows(BizException.class, () -> authService.authenticate("user1", "p1"));
    }

    @Test
    public void testInvalidate() {
        String token = Base64.getEncoder().encodeToString(("user1" + "-" + Base64.getEncoder().encodeToString("pass1".getBytes())).getBytes());
        Assert.assertThrows(BizException.class, () -> authService.invalidate(token));
        CacheManager.TOKEN_CACHE.put(token, "user1");
        Assert.assertThrows(BizException.class, () -> authService.invalidate(token));
    }

    @Test
    public void testCheckRole() {
        String token = Base64.getEncoder().encodeToString(("user2" + "-" + Base64.getEncoder().encodeToString("pass2".getBytes())).getBytes());
        CacheManager.TOKEN_CACHE.put(token, "user2");
        Assert.assertThrows(BizException.class, () -> authService.checkRole("1", "role2"));
        Assert.assertTrue(!authService.checkRole(token, "role2"));
        CacheManager.USER_ROLE_MAP.put("user2", List.of("role2"));
        Assert.assertTrue(!authService.checkRole(token, "role2"));
    }

    @Test
    public void testGetRoles() {
        Role role = new Role().setRoleName("role3");
        String token = Base64.getEncoder().encodeToString(("user3" + "-" + Base64.getEncoder().encodeToString("pass3".getBytes())).getBytes());
        CacheManager.TOKEN_CACHE.put(token, "user3");
        Assert.assertTrue(authService.getRoles(token).isEmpty());
        CacheManager.USER_ROLE_MAP.put("user3", List.of("role3"));
        CacheManager.ROLE_MAP.put("role3", role);
        Assert.assertTrue(authService.getRoles(token).contains(role));
    }
}
