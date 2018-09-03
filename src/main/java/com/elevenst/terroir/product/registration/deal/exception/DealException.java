package com.elevenst.terroir.product.registration.deal.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class DealException extends TerroirException {
    private static final String MSG_PREFIX = "[" + DealException.class.getName().replace("Exception","") + "]";

    public DealException(String msg) { super(MSG_PREFIX + msg);}

    public DealException(Throwable cause) { super(MSG_PREFIX, cause); }

    public DealException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public DealException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public DealException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public DealException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
