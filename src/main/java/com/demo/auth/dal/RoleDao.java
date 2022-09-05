package com.demo.auth.dal;

import com.demo.auth.core.CacheManager;
import com.demo.auth.entity.Role;

import java.util.List;

public class RoleDao {
    public Role getRoleByName(String roleName) {
        return CacheManager.ROLE_MAP.get(roleName);
    }

}
