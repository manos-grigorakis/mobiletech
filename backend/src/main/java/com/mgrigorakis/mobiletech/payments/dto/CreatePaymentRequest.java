package com.mgrigorakis.mobiletech.payments.dto;

import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequest(
        @NotNull(message = "Order ID is required")
        Long orderId,

        @NotNull(message = "Payment provider is required")
        PaymentProviderType paymentProvider
) {}
