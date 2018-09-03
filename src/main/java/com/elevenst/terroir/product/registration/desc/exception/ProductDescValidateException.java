package com.elevenst.terroir.product.registration.desc.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class ProductDescValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + ProductDescValidateException.class.getName().replace("Exception","") + "]";

    public ProductDescValidateException(String msg) { super(MSG_PREFIX + msg);}

    public ProductDescValidateException(Throwable cause) { super(MSG_PREFIX, cause); }

    public ProductDescValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public ProductDescValidateException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public ProductDescValidateException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public ProductDescValidateException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }


}
