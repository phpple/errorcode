package com.github.phpple.errorcode.lib.exception;

import com.github.phpple.errorcode.lib.errorcode.IWebErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class WebException extends BaseException{
    private HttpStatus httpStatus = HttpStatus.OK;

    public WebException(IWebErrorCode errorCode, Map<String, String> args) {
        super(errorCode, args);
    }

    public WebException(IWebErrorCode errorCode) {
        super(errorCode);
    }

    public WebException(IWebErrorCode errorCode, String message, Map<String, String> args) {
        super(errorCode, message, args);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpCode) {
        this.httpStatus = httpCode;
    }
}
