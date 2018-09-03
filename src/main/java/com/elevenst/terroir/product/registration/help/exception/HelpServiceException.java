package com.elevenst.terroir.product.registration.help.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.help.service.HelpServiceImpl;

public class HelpServiceException extends TerroirException {
    private static final String MSG_PREFIX = "[" + HelpServiceImpl.class.getName().replace("Exception", "") + "]";

    public HelpServiceException(String msg) {
        super(MSG_PREFIX + msg);
    }

    public HelpServiceException(Throwable cause) {
        super(MSG_PREFIX, cause);
    }

    public HelpServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public HelpServiceException(ExceptionEnumTypes exceptionEnumTypes) {
        super(MSG_PREFIX + exceptionEnumTypes.getMessage());
    }

    public HelpServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public HelpServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
