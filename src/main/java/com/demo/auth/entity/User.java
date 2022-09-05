package com.demo.auth.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户类
 */
@Data
@Accessors(chain = true)
public class User {
    private String userName;

    private String password;
}
