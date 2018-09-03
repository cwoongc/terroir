package com.elevenst.terroir.product.registration.pln_prd.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;


public class PlanProductControllerException extends TerroirException{

    private static final String MSG_PREFIX = "[" + PlanProductControllerException.class.getName().replace("Exception","") + "]";

    public PlanProductControllerException(String msg) { super(MSG_PREFIX + msg);}

    public PlanProductControllerException(Throwable cause) { super(MSG_PREFIX, cause); }

    public PlanProductControllerException(String message, Throwable exception) {super(MSG_PREFIX + message, exception); }

    public PlanProductControllerException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public PlanProductControllerException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public PlanProductControllerException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }

}
