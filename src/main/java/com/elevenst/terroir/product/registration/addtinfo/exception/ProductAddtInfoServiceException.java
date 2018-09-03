package com.elevenst.terroir.product.registration.addtinfo.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;


public class ProductAddtInfoServiceException extends TerroirException {

    private static final String MSG_PREFIX = "[" + ProductAddtInfoServiceException.class.getName().replace("Exception","") + "]";

    public ProductAddtInfoServiceException(String msg) { super(MSG_PREFIX + msg);}

    public ProductAddtInfoServiceException(Throwable cause) { super(MSG_PREFIX, cause); }

    public ProductAddtInfoServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public ProductAddtInfoServiceException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public ProductAddtInfoServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public ProductAddtInfoServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
