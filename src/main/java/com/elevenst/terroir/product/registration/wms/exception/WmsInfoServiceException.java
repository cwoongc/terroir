package com.elevenst.terroir.product.registration.wms.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.wms.service.WmsServiceImpl;

public class WmsInfoServiceException extends TerroirException {

    private static final String MSG_PREFIX = "[" + WmsServiceImpl.class.getName().replace("Exception","") + "]";


    public WmsInfoServiceException(String msg) {
        super(MSG_PREFIX + msg);
    }

    public WmsInfoServiceException(Throwable cause) {
        super(MSG_PREFIX, cause);
    }

    public WmsInfoServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public WmsInfoServiceException(ExceptionEnumTypes exceptionEnumTypes) {
        super(MSG_PREFIX + exceptionEnumTypes.getMessage());
    }

    public WmsInfoServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public WmsInfoServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
