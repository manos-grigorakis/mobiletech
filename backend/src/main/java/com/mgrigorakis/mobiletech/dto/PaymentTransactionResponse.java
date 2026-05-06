package com.mgrigorakis.mobiletech.dto;

import com.mgrigorakis.mobiletech.model.enums.PaymentProvider;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentTransactionResponse(
        Long id,
        PaymentProvider paymentProvider,
        PaymentStatus paymentStatus,
        BigDecimal amount
) {}
