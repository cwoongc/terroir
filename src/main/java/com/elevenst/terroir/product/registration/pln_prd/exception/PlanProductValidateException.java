package com.elevenst.terroir.product.registration.pln_prd.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;

public class PlanProductValidateException extends TerroirException {

    private static final String MSG_PREFIX = "[" + PlanProductValidateException.class.getName().replace("Exception","") + "]";

    public PlanProductValidateException(String msg) { super(MSG_PREFIX + msg);}

    public PlanProductValidateException(Throwable cause) { super(MSG_PREFIX, cause); }

    public PlanProductValidateException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public PlanProductValidateException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public PlanProductValidateException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public PlanProductValidateException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }


}