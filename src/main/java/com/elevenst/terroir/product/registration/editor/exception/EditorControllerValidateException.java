package com.elevenst.terroir.product.registration.editor.exception;

import com.elevenst.exception.TerroirException;

public class EditorControllerValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + EditorControllerValidateException.class.getName().replace("Exception","") + "]";

    public EditorControllerValidateException(String msg) { super(MSG_PREFIX + msg);}

    public EditorControllerValidateException(Throwable cause) { super(MSG_PREFIX, cause); }

    public EditorControllerValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    
}
