package com.github.phpple.errorcode.lib.errorcode;

public interface IErrorCode {
    /**
     * 获取错误码
     * @return
     */
    int getCode();

    /**
     * 获取错误信息
     * @return
     */
    String getMessage();
}
