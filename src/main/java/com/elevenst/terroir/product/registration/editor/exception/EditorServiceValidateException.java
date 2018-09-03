package com.elevenst.terroir.product.registration.editor.exception;

import com.elevenst.exception.TerroirException;

public class EditorServiceValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + EditorServiceValidateException.class.getName().replace("Exception","") + "]";

    public EditorServiceValidateException(String msg) { super(MSG_PREFIX + msg);}

    public EditorServiceValidateException(Throwable cause) { super(MSG_PREFIX, cause); }

    public EditorServiceValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    
}
