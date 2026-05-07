package com.mgrigorakis.mobiletech.payments.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.payments.service.PaymentService;
import com.mgrigorakis.mobiletech.payments.dto.PaypalCaptureResponse;
import com.mgrigorakis.mobiletech.payments.dto.PaypalOrderRequest;
import com.mgrigorakis.mobiletech.payments.dto.PaypalOrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    private final PaymentService<PaypalOrderResponse, PaypalCaptureResponse> paymentService;

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
}
