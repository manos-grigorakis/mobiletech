package com.mgrigorakis.mobiletech.payments.dto;

public record StripePaymentIntentResponse(String clientSecret, String paymentIntentId) {
}
