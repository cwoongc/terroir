package com.elevenst.terroir.product.registration.editor.exception;

import com.elevenst.exception.TerroirException;

public class EditorControllerException extends TerroirException {

    private static final String MSG_PREFIX = "[" + EditorControllerException.class.getName().replace("Exception","") + "]";

    public EditorControllerException(String msg) { super(MSG_PREFIX + msg);}

    public EditorControllerException(Throwable cause) { super(MSG_PREFIX, cause); }

    public EditorControllerException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public EditorControllerException(String message, Throwable exception, int withOutPrefix) {
        super(message, exception);
    }

    
}
