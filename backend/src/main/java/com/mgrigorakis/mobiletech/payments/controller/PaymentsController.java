package com.mgrigorakis.mobiletech.payments.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.payments.dto.*;
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
    private final StripeServiceImpl stripeService;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/stripe/create-payment-intent")
    public ApiResponse<StripePaymentIntentResponse> createStripePaymentIntent(
            @RequestBody @Valid StripePaymentIntentRequest request) {
        return new ApiResponse<>(stripeService.createStripePaymentIntent(request));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/stripe/webhook")
    public void captureStripePayment(
            @RequestBody @Valid String request, @RequestHeader("Stripe-Signature") String sigHeader) {
        stripeService.handleWebhook(request, sigHeader);
    }
}
