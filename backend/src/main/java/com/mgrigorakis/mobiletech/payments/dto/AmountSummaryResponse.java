package com.mgrigorakis.mobiletech.payments.dto;

import java.math.BigDecimal;

public record AmountSummaryResponse(BigDecimal grossAmount, BigDecimal paypalFee, BigDecimal netAmount) {}
