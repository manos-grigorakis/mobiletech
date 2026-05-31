package com.mgrigorakis.mobiletech.dto;

import com.mgrigorakis.mobiletech.model.enums.OrderStatus;

public record OrderByStatusResponse(OrderStatus orderStatus, Long count) {
}
