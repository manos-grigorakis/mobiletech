package com.mgrigorakis.mobiletech.dto;

import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentTransactionResponse(
        Long id,
        PaymentProviderType paymentProvider,
        PaymentStatus paymentStatus,
        BigDecimal grossAmount,
        BigDecimal providerFeeAmount,
        BigDecimal netAmount
) {}
