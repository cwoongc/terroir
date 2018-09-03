package com.elevenst.terroir.product.registration.catalog.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class CatalogException extends TerroirException {
//    private static final String MSG_PREFIX = "[" + CatalogException.class.getName().replace("Exception", "") + "]";
    private static final String MSG_PREFIX = "";

    public CatalogException(String msg) { super(MSG_PREFIX + msg);}

    public CatalogException(Throwable cause) { super(MSG_PREFIX, cause); }

    public CatalogException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public CatalogException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public CatalogException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public CatalogException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
}
