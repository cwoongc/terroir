package com.elevenst.exception;

public class TerroirException extends RuntimeException {

//    private static final String MSG_PREFIX = "[" + TerroirException.class.getName().replace("Exception","") + "]";
    private static final String MSG_PREFIX = "";

    public TerroirException(String msg) { super(MSG_PREFIX + msg);}

    public TerroirException(Throwable cause) { super(MSG_PREFIX, cause); }

    public TerroirException(String message, Throwable exception) {
        super(MSG_PREFIX + message, exception);
    }

    public TerroirException(ExceptionEnumTypes exceptionEnumTypes){ super(MSG_PREFIX + exceptionEnumTypes.getMessage());}

    public TerroirException(ExceptionEnumTypes exceptionEnumTypes, String msg){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msg));
    }

    public TerroirException(ExceptionEnumTypes exceptionEnumTypes, String[] msgs){
        super(replaceErrorMsg(exceptionEnumTypes.getMessage(), MSG_PREFIX + msgs));
    }




    protected static String replaceErrorMsg(String template, String[] msgs){
        if(msgs != null){
            for(int i=0, size=msgs.length;i<size;i++){
                template = template.replaceAll("\\{\\$"+(i+1)+"}", msgs[i]);
            }
        }
        return template;
    }

    protected static String replaceErrorMsg(String template, String msg){
        if(template.indexOf("{$1}") >= 0){
            template = template.replaceAll("\\{\\$1}", msg);
        }else{
            template = template + msg;
        }
        return template;
    }

}
