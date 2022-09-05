package com.demo.auth.dal;

import com.demo.auth.common.ResultCode;
import com.demo.auth.core.CacheManager;
import com.demo.auth.entity.User;
import com.demo.auth.exception.BizException;

import java.util.Objects;

public class UserDao {
    public User getUserByName(String user) {
        return CacheManager.USER_MAP.get(user);
    }

    public void createUser(User user) {
        if (Objects.isNull(getUserByName(user.getUserName()))) {
            CacheManager.USER_MAP.put(user.getUserName(), user);
        } else {
            throw new BizException(ResultCode.USER_EXIST);
        }
    }

    public void deleteUser(String userName) {
        if (Objects.isNull(getUserByName(userName))) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        } else {
            CacheManager.USER_MAP.remove(userName);
            CacheManager.USER_ROLE_MAP.remove(userName);
        }
    }
}
