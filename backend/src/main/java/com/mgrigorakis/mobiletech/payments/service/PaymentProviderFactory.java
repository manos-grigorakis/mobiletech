package com.mgrigorakis.mobiletech.payments.service;

import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentProviderFactory {
    private final Map<PaymentProviderType, PaymentProvider> providers;

    public PaymentProviderFactory(List<PaymentProvider> providers) {
        this.providers = providers.stream().collect(Collectors.toMap(PaymentProvider::getType, Function.identity()));
    }

    public PaymentProvider getProvider(PaymentProviderType type) {return providers.get(type);}
}
