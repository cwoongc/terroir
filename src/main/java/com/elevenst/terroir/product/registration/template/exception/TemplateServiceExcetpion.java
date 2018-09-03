package com.elevenst.terroir.product.registration.template.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class TemplateServiceExcetpion extends TerroirException {
    private static final String MSG_PREFIX = "[" + TemplateServiceExcetpion.class.getName().replace("Exception","") + "]";

    public TemplateServiceExcetpion(String msg) {
        super(MSG_PREFIX + msg);
    }

    public TemplateServiceExcetpion(Throwable cause) {
        super(MSG_PREFIX, cause);
    }

    public TemplateServiceExcetpion(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public TemplateServiceExcetpion(ExceptionEnumTypes exceptionEnumTypes) {
        super(MSG_PREFIX + exceptionEnumTypes.getMessage());
    }

    public TemplateServiceExcetpion(ExceptionEnumTypes exceptionEnumTypes, String msg) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public TemplateServiceExcetpion(ExceptionEnumTypes exceptionEnumTypes, String[] msgs) {
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
