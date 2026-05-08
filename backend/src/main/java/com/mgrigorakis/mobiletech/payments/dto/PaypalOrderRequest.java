package com.mgrigorakis.mobiletech.payments.dto;

import jakarta.validation.constraints.NotNull;

public record PaypalOrderRequest(
        @NotNull(message = "Order ID is required")
        Long orderId
) {}
