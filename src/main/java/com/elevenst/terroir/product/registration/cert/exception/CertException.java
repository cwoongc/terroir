package com.elevenst.terroir.product.registration.cert.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class CertException extends TerroirException{

    private static final String MSG_PREFIX = "[" + CertException.class.getName().replace("Exception", "") + "]";

    public CertException(String msg) { super(MSG_PREFIX + msg);}

    public CertException(Throwable cause) { super(MSG_PREFIX, cause); }

    public CertException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public CertException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public CertException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public CertException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }

}
