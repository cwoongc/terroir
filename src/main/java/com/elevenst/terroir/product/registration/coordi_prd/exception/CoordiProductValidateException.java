package com.elevenst.terroir.product.registration.coordi_prd.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class CoordiProductValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + CoordiProductValidateException.class.getName().replace("Exception","") + "]";

    public CoordiProductValidateException(String msg) { super(MSG_PREFIX + msg);}

    public CoordiProductValidateException(Throwable cause) { super(MSG_PREFIX, cause); }

    public CoordiProductValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public CoordiProductValidateException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public CoordiProductValidateException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public CoordiProductValidateException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
