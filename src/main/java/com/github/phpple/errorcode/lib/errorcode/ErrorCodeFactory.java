package com.github.phpple.errorcode.lib.errorcode;

import com.github.phpple.errorcode.lib.util.ClassScanner;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class ErrorCodeFactory {
    private static Map<Integer, IWebErrorCode> errorCodeMap = new ConcurrentHashMap<>();

    /**
     * 初始化WebErrorCode
     * @param packageName
     */
    public static void initWebErrorCodes(String packageName) {
        Predicate<String> packagePredicate = s -> true;
        Predicate<Class> classPredicate = c -> IWebErrorCode.class.isAssignableFrom(c);

        ClassScanner scanner = new ClassScanner(packageName, true, packagePredicate, classPredicate);
        Set<Class<?>> packageAllClasses = null;
        try {
            packageAllClasses = scanner.doScanAllClasses();
            packageAllClasses.forEach(c -> {
                for (Object enumConstant : c.getEnumConstants()) {
                    IWebErrorCode errorCode = (IWebErrorCode) enumConstant;
                    if (errorCode.getServiceErrorCodes() != null) {
                        bind(errorCode, errorCode.getServiceErrorCodes());
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 绑定web异常码到service异常码
     * @param webErrorCode
     * @param serviceErrorCodes
     */
    public static void bind(IWebErrorCode webErrorCode, IServiceErrorCode[] serviceErrorCodes) {
        for (IServiceErrorCode serviceErrorCode : serviceErrorCodes) {
            errorCodeMap.put(serviceErrorCode.getCode(), webErrorCode);
        }
    }

    /**
     * 获取对应的web的errorCode
     * @param errorCode
     * @return
     */
    public static IWebErrorCode get(IServiceErrorCode errorCode) {
        return get(errorCode.getCode());
    }

    /**
     * 通过错误码获取WebErrorCode
     * @param errorCode
     * @return
     */
    public static IWebErrorCode get(Integer errorCode) {
        if (errorCodeMap.containsKey(errorCode)) {
            return errorCodeMap.get(errorCode);
        }
        return null;
    }
}
