package com.elevenst.terroir.product.registration.ctgrattr.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.coordi_prd.exception.CoordiProductServiceException;

public class CtgrAttrServiceException extends TerroirException {

    private static final String MSG_PREFIX = "[" + CoordiProductServiceException.class.getName().replace("Exception","") + "]";

    public CtgrAttrServiceException(String msg) { super(MSG_PREFIX + msg);}

    public CtgrAttrServiceException(Throwable cause) { super(MSG_PREFIX, cause); }

    public CtgrAttrServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public CtgrAttrServiceException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public CtgrAttrServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public CtgrAttrServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
