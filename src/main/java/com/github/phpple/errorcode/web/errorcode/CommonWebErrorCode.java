package com.github.phpple.errorcode.web.errorcode;

import com.github.phpple.errorcode.lib.errorcode.IServiceErrorCode;
import com.github.phpple.errorcode.lib.errorcode.IWebErrorCode;
import com.github.phpple.errorcode.service.errorcode.FooServiceErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonWebErrorCode implements IWebErrorCode {
    BAD_PARAMETER(1_100_00001, "Bad parameter", null),
    NOT_FOUND(1_100_00002, "Target is not found.id:${id}", new IServiceErrorCode[]{FooServiceErrorCode.FOO_NOT_FOUND}),
    ;

    private int code;
    private String message;
    private IServiceErrorCode[] serviceErrorCodes;
}
