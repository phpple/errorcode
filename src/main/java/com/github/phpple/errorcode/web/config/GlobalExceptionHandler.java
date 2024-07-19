package com.github.phpple.errorcode.web.config;

import com.github.phpple.errorcode.lib.errorcode.ErrorCodeFactory;
import com.github.phpple.errorcode.lib.errorcode.IWebErrorCode;
import com.github.phpple.errorcode.lib.exception.ServiceException;
import com.github.phpple.errorcode.lib.exception.WebException;
import com.github.phpple.errorcode.lib.http.WebResponse;
import com.github.phpple.errorcode.lib.util.SimpleTpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<WebResponse> handleServiceException(ServiceException e) {
        int rawCode = e.getCode();
        IWebErrorCode webErrorCode = ErrorCodeFactory.get(rawCode);
        log.info("web error code:{}", webErrorCode);
        WebResponse response = new WebResponse();
        response.setRawCode(rawCode);
        if (webErrorCode == null) {
            response.setCode(WebResponse.ERROR);
            response.setMessage(WebResponse.ERROR_MESSAGE);
        } else {
            response.setCode(webErrorCode.getCode());
            response.setMessage(SimpleTpl.compile(webErrorCode.getMessage(), e.getArgs()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(WebException.class)
    public ResponseEntity<WebResponse> handlerWebServiceException(WebException e) {
        WebResponse response = new WebResponse();
        response.setCode(e.getCode());
        response.setMessage(SimpleTpl.compile(e.getMessage(), e.getArgs()));
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @PostConstruct
    public void before() {
        log.info("init GlobalExceptionHandler");
        ErrorCodeFactory.initWebErrorCodes("com.github.phpple.errorcode.web.errorcode");
    }
}
