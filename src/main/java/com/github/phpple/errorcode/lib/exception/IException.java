package com.github.phpple.errorcode.lib.exception;

import java.util.Map;

public interface IException {
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

    /**
     * 获取参数
     * @return
     */
    Map<String, String> getArgs();
}
