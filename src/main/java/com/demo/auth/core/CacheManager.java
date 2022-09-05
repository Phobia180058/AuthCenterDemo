package com.demo.auth.core;

import com.demo.auth.entity.Role;
import com.demo.auth.entity.User;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CacheManager {

    /**
     * 存放Token的缓存，key为Token，value就是userName
     */
    public static Cache<String, String> TOKEN_CACHE = Caffeine.newBuilder()
            .initialCapacity(500)
            .maximumSize(1000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    /**
     * 存放User的缓存，key为userName，value就是User
     */
    public static ConcurrentHashMap<String, User> USER_MAP = new ConcurrentHashMap<>();

    /**
     * 存放Role的缓存，key为userName，value为Role
     */
    public static ConcurrentHashMap<String, Role> ROLE_MAP = new ConcurrentHashMap<>();

    /**
     * 存放user-role映射关系，key为userName，value为roleName
     */
    public static ConcurrentHashMap<String, List<String>> USER_ROLE_MAP = new ConcurrentHashMap<>();
}
