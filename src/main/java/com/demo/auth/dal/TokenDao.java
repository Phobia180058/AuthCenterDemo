package com.demo.auth.dal;

import com.demo.auth.core.CacheManager;

public class TokenDao {
    public void save(String userName, String token) {
        CacheManager.TOKEN_CACHE.put(token, userName);
    }

    public String getUserByToken(String token) {
        return CacheManager.TOKEN_CACHE.getIfPresent(token);
    }

    public void remove(String token) {
        CacheManager.TOKEN_CACHE.invalidate(token);
    }
}
