package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.model.Order;
import com.mgrigorakis.mobiletech.model.PaymentTransaction;
import com.mgrigorakis.mobiletech.repository.OrderRepository;
import com.mgrigorakis.mobiletech.repository.PaymentTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentTransactionServiceImpl implements PaymentTransactionService {
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final OrderRepository orderRepository;

    @Override
    public void createPaymentTransaction(Long orderId, PaymentTransaction paymentTransaction) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.warn("Order id {} not found", orderId);
            return new EntityNotFoundException("Order with order id " + orderId + " not found");
        });

        paymentTransaction.setOrder(order);
        paymentTransactionRepository.save(paymentTransaction);
    }
}
