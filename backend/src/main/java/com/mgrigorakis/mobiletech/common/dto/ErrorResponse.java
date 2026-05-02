package com.mgrigorakis.mobiletech.common.dto;

import java.util.Map;

public record ErrorResponse(
        Integer status,
        String message,
        String errorCode,
        Map<String, Object> details
) {}
