package com.woo.forjob.util.error;

import lombok.Builder;

public class BusinessException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    @Builder
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
