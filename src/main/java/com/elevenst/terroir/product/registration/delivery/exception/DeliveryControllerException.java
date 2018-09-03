package com.elevenst.terroir.product.registration.delivery.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class DeliveryControllerException extends TerroirException {

    private static final String MSG_PREFIX = "[" + DeliveryControllerException.class.getName().replace("Exception", "") + "]";

    public DeliveryControllerException(String msg) { super(MSG_PREFIX + msg);}

    public DeliveryControllerException(Throwable cause) { super(MSG_PREFIX, cause); }

    public DeliveryControllerException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public DeliveryControllerException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public DeliveryControllerException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public DeliveryControllerException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
