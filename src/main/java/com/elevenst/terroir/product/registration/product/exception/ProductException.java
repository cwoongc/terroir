package com.elevenst.terroir.product.registration.product.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class ProductException extends TerroirException {

//    private static final String MSG_PREFIX = "[" + ProductException.class.getName().replace("Exception","") + "]";
    private static final String MSG_PREFIX = "";

    public ProductException(String msg) { super(MSG_PREFIX + msg);}

    public ProductException(Throwable cause) { super(MSG_PREFIX, cause); }

    public ProductException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public ProductException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public ProductException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public ProductException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), msgs));
    }

}
