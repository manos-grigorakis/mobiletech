package com.mgrigorakis.mobiletech.dto;

import java.math.BigDecimal;

public record OrderItemSummaryResponse(
        Long id,
        BigDecimal price,
        Integer quantity,
        Long productId
) {}
