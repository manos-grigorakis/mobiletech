package com.mgrigorakis.mobiletech.payments.dto;

import java.math.BigDecimal;

public record PaypalCaptureResponse(
        String orderId,
        String status,
        BigDecimal grossAmount,
        BigDecimal paypalFee,
        BigDecimal netAmount
) {}
