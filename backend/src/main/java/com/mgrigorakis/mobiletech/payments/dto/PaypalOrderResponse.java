package com.mgrigorakis.mobiletech.payments.dto;

public record PaypalOrderResponse(String paypalOrderId, String status, String approveUrl) {}
