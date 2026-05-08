package com.mgrigorakis.mobiletech.payments.dto;

import com.mgrigorakis.mobiletech.model.enums.OrderStatus;

public record CashOnDeliveryResponse(
        Long orderId,
        OrderStatus status,
        AmountSummaryResponse amountSummary
) {}
