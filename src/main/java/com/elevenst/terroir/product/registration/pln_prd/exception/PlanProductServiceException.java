package com.elevenst.terroir.product.registration.pln_prd.exception;

import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.exception.TerroirException;


public class PlanProductServiceException extends TerroirException{

    private static final String MSG_PREFIX = "[" + PlanProductServiceException.class.getName().replace("Exception","") + "]";

    public PlanProductServiceException(String msg) { super(MSG_PREFIX + msg);}

    public PlanProductServiceException(Throwable cause) { super(MSG_PREFIX, cause); }

    public PlanProductServiceException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public PlanProductServiceException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public PlanProductServiceException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public PlanProductServiceException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }
    
    
    
    
}
