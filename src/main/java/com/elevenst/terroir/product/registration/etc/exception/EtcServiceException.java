package com.elevenst.terroir.product.registration.etc.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.etc.service.EtcServiceImpl;

public class EtcServiceException extends TerroirException {
    private static final String MSG_PREFIX = "[" + EtcServiceImpl.class.getName().replace("Exception","") + "]";


    public EtcServiceException(String msg) {
        super(MSG_PREFIX + msg);
    }

    public EtcServiceException(Throwable cause) {
        super(MSG_PREFIX, cause);
    }

    public EtcServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public EtcServiceException(ExceptionEnumTypes exceptionEnumTypes) {
        super(MSG_PREFIX + exceptionEnumTypes.getMessage());
    }

    public EtcServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public EtcServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
