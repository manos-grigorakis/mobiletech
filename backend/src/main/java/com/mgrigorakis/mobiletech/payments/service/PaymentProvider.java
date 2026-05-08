package com.mgrigorakis.mobiletech.payments.service;

import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import com.mgrigorakis.mobiletech.payments.dto.CreatePaymentRequest;

public interface PaymentProvider {
    PaymentProviderType getType();

    Object createPayment(CreatePaymentRequest request);

    void handleWebhook(String payload, String signature);
}
