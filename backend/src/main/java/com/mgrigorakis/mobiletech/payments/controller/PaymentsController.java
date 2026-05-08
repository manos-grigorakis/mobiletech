package com.mgrigorakis.mobiletech.payments.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import com.mgrigorakis.mobiletech.payments.dto.*;
import com.mgrigorakis.mobiletech.payments.service.PaymentProvider;
import com.mgrigorakis.mobiletech.payments.service.PaymentProviderFactory;
import com.mgrigorakis.mobiletech.payments.service.PaymentService;
import com.mgrigorakis.mobiletech.payments.service.StripeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    private final PaymentService<PaypalOrderResponse, PaypalCaptureResponse> paymentService;
    private final PaymentProviderFactory paymentProviderFactory;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ApiResponse<?> createOrder(@RequestBody @Valid CreatePaymentRequest request) {
        PaymentProvider provider = paymentProviderFactory.getProvider(request.paymentProvider());
        return new ApiResponse<>(provider.createPayment(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/paypal/create-order")
    public ApiResponse<PaypalOrderResponse> createOrder(@RequestBody @Valid PaypalOrderRequest request) {
        return new ApiResponse<>(paymentService.createOrder(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/paypal/capture/{paypalOrderId}")
    public ApiResponse<PaypalCaptureResponse> capturePayment(@PathVariable String paypalOrderId) {
        return new ApiResponse<>(paymentService.captureOrder(paypalOrderId));
    }

    // Testing Only simulating PayPal redirect URL
    @GetMapping("/paypal/success")
    public String paypalSuccess(@RequestParam String token) {
        return "Success, TOKEN:  " + token;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/stripe/webhook")
    public void captureStripePayment(
            @RequestBody @Valid String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        paymentProviderFactory.getProvider(PaymentProviderType.STRIPE).handleWebhook(payload, sigHeader);
    }
}
