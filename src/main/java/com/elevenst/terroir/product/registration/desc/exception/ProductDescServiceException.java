package com.elevenst.terroir.product.registration.desc.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class ProductDescServiceException extends TerroirException {

    private static final String MSG_PREFIX = "[" + ProductDescServiceException.class.getName().replace("Exception","") + "]";

    public ProductDescServiceException(String msg) { super(MSG_PREFIX + msg);}

    public ProductDescServiceException(Throwable cause) { super(MSG_PREFIX, cause); }

    public ProductDescServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public ProductDescServiceException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public ProductDescServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public ProductDescServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
