package com.elevenst.terroir.product.registration.etc.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class EtcValidateException extends TerroirException {
    private static final String MSG_PREFIX = "[" + EtcValidateException.class.getName().replace("Exception","") + "]";


    public EtcValidateException(String msg) {
        super(MSG_PREFIX + msg);
    }

    public EtcValidateException(Throwable cause) {
        super(MSG_PREFIX, cause);
    }

    public EtcValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public EtcValidateException(ExceptionEnumTypes exceptionEnumTypes) {
        super(MSG_PREFIX + exceptionEnumTypes.getMessage());
    }

    public EtcValidateException(ExceptionEnumTypes exceptionEnumTypes, String msg) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public EtcValidateException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
