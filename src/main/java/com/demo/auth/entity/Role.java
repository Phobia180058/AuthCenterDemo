package com.demo.auth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class Role {
    private String roleName;
}
