package com.github.phpple.errorcode.lib.errorcode;

/**
 * WebApi的异常码
 */
public interface IWebErrorCode extends IErrorCode {
    /**
     * 获取对应的服务错误码
     */
    IServiceErrorCode[] getServiceErrorCodes();
}
