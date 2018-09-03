package com.elevenst.terroir.product.registration.mobile.message;

import java.io.Serializable;

public class ValidationResult implements Serializable {
    /** 정보 타당성 여부 */
    private boolean success;

    /** 검증 결과 메세지 */
    private String message;

    /** 애러 유형 */
    private String errorType;

    public ValidationResult(boolean success) {
        this(success, "");
    }

    public ValidationResult(boolean success, String message) {
        this(success, message, "");
    }
    public ValidationResult(boolean success, String message, String errorType) {
        this.success= success;
        this.message = message;
        this.errorType = errorType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
