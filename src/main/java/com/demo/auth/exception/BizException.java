package com.demo.auth.exception;

import com.demo.auth.common.ResultCode;

/**
 * 业务异常类
 */
public class BizException extends RuntimeException {
    private final ResultCode resultCode;

    public BizException() {
        this(ResultCode.FAILED);
    }

    public BizException(ResultCode failed) {
        super(failed.getMsg());
        this.resultCode = failed;
    }

    public BizException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }
}
