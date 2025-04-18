package com.edwin.emsp.common.exception;

/**
 * @Author: jiucheng
 * @Description: BizException
 * @Date: 2025/4/12
 */
public class BizException extends RuntimeException {
    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    protected BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}