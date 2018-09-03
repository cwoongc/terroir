package com.elevenst.terroir.product.registration.shop.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class ShopServiceException extends TerroirException {

    private static final String MSG_PREFIX = "[" + ShopServiceException.class.getName().replace("Exception","") + "]";

    public ShopServiceException(String msg) { super(MSG_PREFIX + msg);}

    public ShopServiceException(Throwable cause) { super(MSG_PREFIX, cause); }

    public ShopServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public ShopServiceException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public ShopServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public ShopServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
