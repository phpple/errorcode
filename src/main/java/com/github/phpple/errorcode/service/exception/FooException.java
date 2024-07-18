package com.github.phpple.errorcode.service.exception;

import com.github.phpple.errorcode.lib.errorcode.IServiceErrorCode;
import com.github.phpple.errorcode.lib.exception.ServiceException;

import java.util.Map;

public class FooException extends ServiceException {
    public FooException(IServiceErrorCode errorCode, Map<String, String> args) {
        super(errorCode, args);
    }

    public FooException(IServiceErrorCode errorCode) {
        super(errorCode);
    }

    public FooException(IServiceErrorCode errorCode, String message, Map<String, String> args) {
        super(errorCode, message, args);
    }
}
