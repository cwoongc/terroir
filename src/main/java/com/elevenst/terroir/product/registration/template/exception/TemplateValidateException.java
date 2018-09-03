package com.elevenst.terroir.product.registration.template.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class TemplateValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + TemplateValidateException.class.getName().replace("Exception","") + "]";

    public TemplateValidateException(String msg) {
        super(MSG_PREFIX + msg);
    }

    public TemplateValidateException(Throwable cause) {
        super(MSG_PREFIX, cause);
    }

    public TemplateValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public TemplateValidateException(ExceptionEnumTypes exceptionEnumTypes) {
        super(MSG_PREFIX + exceptionEnumTypes.getMessage());
    }

    public TemplateValidateException(ExceptionEnumTypes exceptionEnumTypes, String msg) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public TemplateValidateException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}