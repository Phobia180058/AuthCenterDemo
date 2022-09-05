package com.demo.auth.service.impl;

import com.demo.auth.common.ResultCode;
import com.demo.auth.dal.RoleDao;
import com.demo.auth.dal.TokenDao;
import com.demo.auth.dal.UserDao;
import com.demo.auth.dal.UserRoleDao;
import com.demo.auth.entity.Role;
import com.demo.auth.entity.User;
import com.demo.auth.exception.BizException;
import com.demo.auth.service.AuthService;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuthServiceImpl implements AuthService {
    private RoleDao roleDao;

    private UserDao userDao;

    private UserRoleDao userRoleDao;

    private TokenDao tokenDao;

    public AuthServiceImpl() {
        roleDao = new RoleDao();
        userDao = new UserDao();
        userRoleDao = new UserRoleDao();
        tokenDao = new TokenDao();
    }

    @Override
    public void createUser(String userName, String pass) {
        pass = Base64.getEncoder().encodeToString(pass.getBytes());
        userDao.createUser(new User()
                .setUserName(userName)
                .setPassword(pass));
    }

    @Override
    public void deleteUser(String userName) {
        userDao.deleteUser(userName);
    }

    @Override
    public void addRole(String userName, String roleName) {
        User user = userDao.getUserByName(userName);

        if (Objects.isNull(user)) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }

        userRoleDao.addRole(userName, roleName);
    }

    @Override
    public String authenticate(String userName, String password) {
        User user = userDao.getUserByName(userName);

        if (Objects.isNull(user)) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }

        if (Base64.getEncoder().encodeToString(password.getBytes()).equals(user.getPassword())) {
            String token = Base64.getEncoder().encodeToString((user.getUserName() + "-" + user.getPassword()).getBytes());
            tokenDao.save(userName, token);
            return token;
        } else {
            throw new BizException(ResultCode.WRONG_PASS);
        }
    }

    @Override
    public void invalidate(String token) {
        if ("".equals(tokenDao.getUserByToken(token)) || Objects.isNull(tokenDao.getUserByToken(token))) {
            throw new BizException(ResultCode.USER_NOT_AUTH);
        }

        tokenDao.remove(token);
    }

    @Override
    public boolean checkRole(String token, String roleName) {
        String userName = tokenDao.getUserByToken(token);
        if ("".equals(tokenDao.getUserByToken(token)) || Objects.isNull(tokenDao.getUserByToken(token))) {
            throw new BizException(ResultCode.USER_NOT_AUTH);
        }

        User user = userDao.getUserByName(userName);
        if (Objects.isNull(user)) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        List<String> userRoleList = userRoleDao.getRoleByUser(userName);
        if (!userRoleList.contains(roleName)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<Role> getRoles(String token) {
        String userName = tokenDao.getUserByToken(token);
        if ("".equals(tokenDao.getUserByToken(token)) || Objects.isNull(tokenDao.getUserByToken(token))) {
            throw new BizException(ResultCode.USER_NOT_AUTH);
        }

        return userRoleDao.getRoleByUser(userName).stream()
                .map(s -> roleDao.getRoleByName(s))
                .filter(role -> !Objects.isNull(role))
                .collect(Collectors.toList());
    }
}
