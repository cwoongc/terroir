package com.elevenst.terroir.product.registration.delivery.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class DeliveryServiceException extends TerroirException {

    private static final String MSG_PREFIX = "[" + DeliveryServiceException.class.getName().replace("Exception", "") + "]";

    public DeliveryServiceException(String msg) { super(MSG_PREFIX + msg);}

    public DeliveryServiceException(Throwable cause) { super(MSG_PREFIX, cause); }

    public DeliveryServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public DeliveryServiceException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public DeliveryServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public DeliveryServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
