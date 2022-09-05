package com.demo.auth.service;

import com.demo.auth.entity.Role;

import java.util.List;

/**
 * 使用方式就是自己构造一个对象，控制单例或者不控制as you like
 * 一些边界条件，包括trim等过于麻烦没有做校验，还有统一异常控制只往上抛了没有做处理
 * 密码加盐用了最简单的Base64
 */
public interface AuthService {
    void createUser(String userName, String pass);

    void deleteUser(String userName);

    void addRole(String userName, String roleName);

    String authenticate(String userName, String password);

    void invalidate(String token);

    boolean checkRole(String token, String roleName);

    List<Role> getRoles(String token);
}
