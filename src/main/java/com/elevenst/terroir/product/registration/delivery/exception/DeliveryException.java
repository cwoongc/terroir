package com.elevenst.terroir.product.registration.delivery.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class DeliveryException extends TerroirException {

//    private static final String MSG_PREFIX = "[" + DeliveryException.class.getName().replace("Exception", "") + "]";
    private static final String MSG_PREFIX = "";

    public DeliveryException(String msg) { super(MSG_PREFIX + msg);}

    public DeliveryException(Throwable cause) { super(MSG_PREFIX, cause); }

    public DeliveryException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public DeliveryException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public DeliveryException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public DeliveryException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
