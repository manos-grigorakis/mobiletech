package com.mgrigorakis.mobiletech.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    PAYMENT_FAILED("payment_failed"),
    PROCESSING("processing"),
    SHIPPED("shipped"),
    DELIVERED("delivered"),
    CANCELED("canceled");

    private final String value;

    @JsonValue
    public String getValue() {return  value;}
}
