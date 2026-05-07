package com.mgrigorakis.mobiletech.payments.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaypalOrderRequest(
        @NotNull(message = "Order ID is required")
        Long orderId,

        @NotNull(message = "Amount is required")
        @Digits(integer = 17, fraction = 2)
        @Positive
        BigDecimal amount
) {}
