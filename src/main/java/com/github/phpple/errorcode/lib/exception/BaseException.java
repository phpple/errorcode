package com.github.phpple.errorcode.lib.exception;

import com.github.phpple.errorcode.lib.errorcode.IErrorCode;

import java.util.Map;

public abstract class BaseException extends RuntimeException implements IException{
    private int code;

    private Map<String, String> args;

    public BaseException(IErrorCode errorCode, Map<String, String> args) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.args = args;
    }

    public BaseException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.args = null;
    }

    public BaseException(IErrorCode errorCode, String message, Map<String, String> args) {
        super(message);
        this.code = errorCode.getCode();
        this.args = args;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public Map<String, String> getArgs() {
        return this.args;
    }
}
