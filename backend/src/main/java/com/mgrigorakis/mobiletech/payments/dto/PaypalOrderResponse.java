package com.mgrigorakis.mobiletech.payments.dto;

public record PaypalOrderResponse(String orderId, String status, String approveUrl) {}
