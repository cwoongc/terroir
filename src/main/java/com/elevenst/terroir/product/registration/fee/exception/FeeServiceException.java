package com.elevenst.terroir.product.registration.fee.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.fee.service.FeeServiceImpl;

public class FeeServiceException extends TerroirException {
    private static final String MSG_PREFIX = "[" + FeeServiceImpl.class.getName().replace("Exception","") + "]";


    public FeeServiceException(String msg) {
        super(MSG_PREFIX + msg);
    }

    public FeeServiceException(Throwable cause) {
        super(MSG_PREFIX, cause);
    }

    public FeeServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public FeeServiceException(ExceptionEnumTypes exceptionEnumTypes) {
        super(MSG_PREFIX + exceptionEnumTypes.getMessage());
    }

    public FeeServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public FeeServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
