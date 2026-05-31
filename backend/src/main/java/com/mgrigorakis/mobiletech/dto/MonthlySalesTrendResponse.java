package com.mgrigorakis.mobiletech.dto;

import java.math.BigDecimal;

public record MonthlySalesTrendResponse(
        Integer  month,
        Integer  year,
        BigDecimal revenue) {
}
