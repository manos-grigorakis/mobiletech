package com.mgrigorakis.mobiletech.payments.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import com.mgrigorakis.mobiletech.payments.dto.*;
import com.mgrigorakis.mobiletech.payments.service.PaymentProvider;
import com.mgrigorakis.mobiletech.payments.service.PaymentProviderFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    private final PaymentProviderFactory paymentProviderFactory;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ApiResponse<?> createOrder(@RequestBody @Valid CreatePaymentRequest request) {
        PaymentProvider provider = paymentProviderFactory.getProvider(request.paymentProvider());
        return new ApiResponse<>(provider.createPayment(request));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/paypal/webhook")
    public void capturePaypalPayment(@RequestBody @Valid String payload) {
        paymentProviderFactory.getProvider(PaymentProviderType.PAYPAL).handleWebhook(payload, "");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/stripe/webhook")
    public void captureStripePayment(
            @RequestBody @Valid String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        paymentProviderFactory.getProvider(PaymentProviderType.STRIPE).handleWebhook(payload, sigHeader);
    }

    @GetMapping("/paypal/success")
    public ResponseEntity<Void> paypalSuccess(@RequestParam String token) {
        String redirectUrl = paymentProviderFactory.getProvider(PaymentProviderType.PAYPAL).completePayment(token);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
    }
}
