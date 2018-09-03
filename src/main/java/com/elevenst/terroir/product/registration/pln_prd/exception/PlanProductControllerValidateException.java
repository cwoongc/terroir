package com.elevenst.terroir.product.registration.pln_prd.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class PlanProductControllerValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + PlanProductControllerValidateException.class.getName().replace("Exception","") + "]";

    public PlanProductControllerValidateException(String msg) { super(MSG_PREFIX + msg);}

    public PlanProductControllerValidateException(Throwable cause) { super(MSG_PREFIX, cause); }

    public PlanProductControllerValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public PlanProductControllerValidateException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public PlanProductControllerValidateException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public PlanProductControllerValidateException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }


}