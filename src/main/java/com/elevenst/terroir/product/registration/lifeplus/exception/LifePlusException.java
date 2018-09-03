package com.elevenst.terroir.product.registration.lifeplus.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class LifePlusException extends TerroirException {

    private static final String MSG_PREFIX = "[" + LifePlusException.class.getName().replace("Exception", "") + "]";

    public LifePlusException(String msg) { super(MSG_PREFIX + msg);}

    public LifePlusException(Throwable cause) { super(MSG_PREFIX, cause); }

    public LifePlusException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public LifePlusException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public LifePlusException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public LifePlusException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
