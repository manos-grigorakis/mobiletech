package com.mgrigorakis.mobiletech.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ConflictException extends RuntimeException {
    private String errorCode = "";
    private final Map<String, Object> details;

    public ConflictException(String message) {
        super(message);
        this.details = Map.of();
    }

    public ConflictException(String message, String errorCode, Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details != null ? Map.copyOf(details) : Map.of();
    }

    public ConflictException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = Map.of();
    }

    public ConflictException(String message, Map<String, Object> details) {
        super(message);
        this.details = details != null ? Map.copyOf(details) : Map.of();
    }
}
