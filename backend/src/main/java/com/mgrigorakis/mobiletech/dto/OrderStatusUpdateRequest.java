package com.mgrigorakis.mobiletech.dto;

import com.mgrigorakis.mobiletech.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequest(
        @NotNull(message = "Status is required")
        OrderStatus status
) {}
