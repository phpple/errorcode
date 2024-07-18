package com.github.phpple.errorcode.lib.exception;

import com.github.phpple.errorcode.lib.errorcode.IServiceErrorCode;

import java.util.Map;

public class ServiceException extends BaseException {

    public ServiceException(IServiceErrorCode errorCode, Map<String, String> args) {
        super(errorCode, args);
    }

    public ServiceException(IServiceErrorCode errorCode) {
        super(errorCode);
    }

    public ServiceException(IServiceErrorCode errorCode, String message, Map<String, String> args) {
        super(errorCode, message, args);
    }
}
