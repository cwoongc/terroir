package com.elevenst.terroir.product.registration.option.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class OptionException extends TerroirException {
//    private static final String MSG_PREFIX = "[" + OptionException.class.getName().replace("Exception", "") + "]";
    private static final String MSG_PREFIX = "";

    public OptionException(String msg) { super(MSG_PREFIX + msg);}

    public OptionException(Throwable cause) { super(MSG_PREFIX, cause); }

    public OptionException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public OptionException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public OptionException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public OptionException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
