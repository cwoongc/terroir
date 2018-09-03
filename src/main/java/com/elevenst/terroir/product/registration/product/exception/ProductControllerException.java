package com.elevenst.terroir.product.registration.product.exception;

import com.elevenst.exception.TerroirException;

public class ProductControllerException extends TerroirException {

    private static final String MSG_PREFIX = "[" + ProductControllerException.class.getName().replace("Exception","") + "]";

    public ProductControllerException(String msg) { super(MSG_PREFIX + msg);}

    public ProductControllerException(Throwable cause) { super(MSG_PREFIX, cause); }

    public ProductControllerException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    
}
