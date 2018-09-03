package com.elevenst.terroir.product.registration.addtinfo.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class ProductAddtInfoValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + ProductAddtInfoValidateException.class.getName().replace("Exception","") + "]";

    public ProductAddtInfoValidateException(String msg) { super(MSG_PREFIX + msg);}

    public ProductAddtInfoValidateException(Throwable cause) { super(MSG_PREFIX, cause); }

    public ProductAddtInfoValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public ProductAddtInfoValidateException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public ProductAddtInfoValidateException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public ProductAddtInfoValidateException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
    
}
