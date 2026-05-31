package com.mgrigorakis.mobiletech.dto;

import java.math.BigDecimal;

public record RevenueByCategoryResponse(String category, BigDecimal amount) {
}
