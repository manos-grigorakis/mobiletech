package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.model.PaymentTransaction;

public interface PaymentTransactionService {
    void createPaymentTransaction(Long orderId, PaymentTransaction paymentTransaction);
}
