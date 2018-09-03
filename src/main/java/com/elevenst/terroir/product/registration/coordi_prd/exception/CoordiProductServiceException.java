package com.elevenst.terroir.product.registration.coordi_prd.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class CoordiProductServiceException extends TerroirException {

    private static final String MSG_PREFIX = "[" + CoordiProductServiceException.class.getName().replace("Exception","") + "]";

    public CoordiProductServiceException(String msg) { super(MSG_PREFIX + msg);}

    public CoordiProductServiceException(Throwable cause) { super(MSG_PREFIX, cause); }

    public CoordiProductServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public CoordiProductServiceException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public CoordiProductServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public CoordiProductServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
