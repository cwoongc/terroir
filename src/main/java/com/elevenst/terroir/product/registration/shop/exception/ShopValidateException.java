package com.elevenst.terroir.product.registration.shop.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class ShopValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + ShopValidateException.class.getName().replace("Exception","") + "]";

    public ShopValidateException(String msg) { super(MSG_PREFIX + msg);}

    public ShopValidateException(Throwable cause) { super(MSG_PREFIX, cause); }

    public ShopValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public ShopValidateException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public ShopValidateException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public ShopValidateException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
