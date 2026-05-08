package com.mgrigorakis.mobiletech.payments.service;

import com.mgrigorakis.mobiletech.dto.OrderResponse;
import com.mgrigorakis.mobiletech.dto.OrderStatusUpdateRequest;
import com.mgrigorakis.mobiletech.model.PaymentTransaction;
import com.mgrigorakis.mobiletech.model.enums.OrderStatus;
import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;
import com.mgrigorakis.mobiletech.payments.dto.AmountSummaryResponse;
import com.mgrigorakis.mobiletech.payments.dto.CashOnDeliveryResponse;
import com.mgrigorakis.mobiletech.payments.dto.CreatePaymentRequest;
import com.mgrigorakis.mobiletech.service.OrderService;
import com.mgrigorakis.mobiletech.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class CashOnDeliveryServiceImpl implements PaymentProvider {
    private final PaymentTransactionService paymentTransactionService;
    private final OrderService orderService;

    @Override
    public PaymentProviderType getType() {
        return PaymentProviderType.CASH_ON_DELIVERY;
    }

    @Override
    public CashOnDeliveryResponse createPayment(CreatePaymentRequest request) {
        OrderResponse orderResponse = orderService.getOrderById(request.orderId());

        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .paymentProvider(PaymentProviderType.CASH_ON_DELIVERY)
                .paymentStatus(PaymentStatus.PENDING)
                .grossAmount(orderResponse.totalAmount())
                .providerFeeAmount(BigDecimal.ZERO)
                .netAmount(orderResponse.totalAmount())
                .build();

        paymentTransactionService.createPaymentTransaction(request.orderId(),  paymentTransaction);
        orderService.updateOrderStatusById(request.orderId(), new OrderStatusUpdateRequest(OrderStatus.CONFIRMED));

        log.info("Cash on delivery order confirmed for orderId: {}", request.orderId());

        AmountSummaryResponse amountSummaryResponse =
                new AmountSummaryResponse(orderResponse.totalAmount(), BigDecimal.ZERO, orderResponse.totalAmount());
        return new CashOnDeliveryResponse(request.orderId(), OrderStatus.CONFIRMED, amountSummaryResponse);
    }

    @Override
    public void handleWebhook(String payload, String signature) {
        // Cash on delivery does not use webhooks
    }
}
