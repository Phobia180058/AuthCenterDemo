package com.demo.auth.common;

public enum ResultCode {
    FAILED(-1, "失败"),
    SUCCESS(0, "成功"),
    USER_EXIST(1, "用户已存在"),
    USER_NOT_EXIST(2, "用户不存在"),
    WRONG_PASS(3, "密码错误"),
    USER_NOT_AUTH(4, "用户未认证");

    ResultCode(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    final private int statusCode;

    final private String msg;
}
