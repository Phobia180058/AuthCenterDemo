package com.demo.auth.dal;

import com.demo.auth.core.CacheManager;

import java.util.ArrayList;
import java.util.List;

public class UserRoleDao {
    public List<String> getRoleByUser(String userName) {
        return CacheManager.USER_ROLE_MAP.containsKey(userName) ?
                CacheManager.USER_ROLE_MAP.get(userName) : new ArrayList<>();
    }

    public void addRole(String userName, String roleName) {
        List<String> roleNames = getRoleByUser(userName);
        roleNames.add(roleName);
        CacheManager.USER_ROLE_MAP.put(userName, roleNames);
    }
}
