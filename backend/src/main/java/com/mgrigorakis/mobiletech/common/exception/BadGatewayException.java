package com.mgrigorakis.mobiletech.common.exception;

import lombok.Getter;

@Getter
public class BadGatewayException extends RuntimeException {
    private String errorCode = "";

    public BadGatewayException(String message) {
        super(message);
    }

    public BadGatewayException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
