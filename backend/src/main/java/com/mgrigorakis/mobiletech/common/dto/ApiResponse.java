package com.mgrigorakis.mobiletech.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public record ApiResponse<T>(
        String transaction,
        T data,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
        Date timestamp,
        ErrorResponse error
) {
    public ApiResponse {
        if (transaction == null) transaction = UUID.randomUUID().toString();
        if (timestamp == null) timestamp = new Date();
    }

    public ApiResponse(T data) {
        this(null, data, null, null);
    }

    public ApiResponse(ErrorResponse error) {
        this(null, null, null, error);
    }
}
