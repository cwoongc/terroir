package com.elevenst.terroir.product.registration.mobile.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class MobileServiceException extends TerroirException {
    private static final String MSG_PREFIX = "[" + MobileServiceException.class.getName().replace("Exception","") + "]";

    public MobileServiceException(String msg) {
        super(MSG_PREFIX + msg);
    }

    public MobileServiceException(Throwable cause) {
        super(MSG_PREFIX, cause);
    }

    public MobileServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public MobileServiceException(ExceptionEnumTypes exceptionEnumTypes) {
        super(MSG_PREFIX + exceptionEnumTypes.getMessage());
    }

    public MobileServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public MobileServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
