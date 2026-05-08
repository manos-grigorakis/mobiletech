package com.mgrigorakis.mobiletech.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentProviderType {
    CASH_ON_DELIVERY("cash_on_delivery"),
    STRIPE("stripe"),
    PAYPAL("paypal");

    private final String value;

    @JsonValue
    public String getValue() {return value;}
}
