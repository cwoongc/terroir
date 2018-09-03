package com.elevenst.terroir.product.registration.editor.exception;

import com.elevenst.exception.TerroirException;

public class EditorServiceException extends TerroirException {

    private static final String MSG_PREFIX = "[" + EditorServiceException.class.getName().replace("Exception","") + "]";

    public EditorServiceException(String msg) { super(MSG_PREFIX + msg);}

    public EditorServiceException(Throwable cause) { super(MSG_PREFIX, cause); }

    public EditorServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    
}
