package com.mgrigorakis.mobiletech.payments.service;


import com.mgrigorakis.mobiletech.payments.dto.PaypalOrderRequest;

public interface PaymentService<T, V> {
    T createOrder(PaypalOrderRequest request);
    V captureOrder(String orderId);
}
