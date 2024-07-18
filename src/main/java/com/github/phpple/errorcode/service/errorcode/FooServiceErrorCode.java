package com.github.phpple.errorcode.service.errorcode;

import com.github.phpple.errorcode.lib.errorcode.IServiceErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FooServiceErrorCode implements IServiceErrorCode {
    FOO_NOT_FOUND(2_100_00001, "Foo not found"),
    FOO_EXISTED(2_100_00002, "Foo is existed"),
    ;

    private int code;
    private String message;
}
