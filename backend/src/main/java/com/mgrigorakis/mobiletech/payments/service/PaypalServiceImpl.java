package com.mgrigorakis.mobiletech.payments.service;

import com.mgrigorakis.mobiletech.common.exception.BadGatewayException;
import com.mgrigorakis.mobiletech.payments.dto.PaypalCaptureResponse;
import com.mgrigorakis.mobiletech.payments.dto.PaypalOrderRequest;
import com.mgrigorakis.mobiletech.payments.dto.PaypalOrderResponse;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ErrorException;
import com.paypal.sdk.http.response.ApiResponse;
import com.paypal.sdk.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.CompletionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaypalServiceImpl implements PaymentService<PaypalOrderResponse, PaypalCaptureResponse> {
    private final PaypalServerSdkClient paypalClient;

    @Value("${app.payments.paypal.redirect-url}")
    private String paypalReturnUrl;

    @Value("${app.payments.paypal.cancel-url}")
    private String paypalCancelUrl;

    @Override
    public PaypalOrderResponse createOrder(PaypalOrderRequest request) {
        OrdersController ordersController = paypalClient.getOrdersController();

        AmountWithBreakdown amount = new AmountWithBreakdown
                .Builder("EUR", request.amount().toPlainString()).build();

        CreateOrderInput createOrderInput = new CreateOrderInput.Builder(null,
                new OrderRequest.Builder(
                        CheckoutPaymentIntent.CAPTURE,
                        Arrays.asList(new PurchaseUnitRequest.Builder(amount)
                                              .customId(request.orderId().toString())
                                              .build()))
                        .applicationContext(new OrderApplicationContext.Builder()
                                                    .returnUrl(paypalReturnUrl)
                                                    .cancelUrl(paypalCancelUrl)
                                                    .build())
                        .build())
                .prefer("return=minimal").build();

        try {
            ApiResponse<Order> response = ordersController.createOrderAsync(createOrderInput).join();

            Order result = response.getResult();
            String approveUrl = result.getLinks().stream()
                    .filter(link -> "approve".equals(link.getRel()))
                    .findFirst()
                    .map(LinkDescription::getHref)
                    .orElseThrow();

            return new PaypalOrderResponse(result.getId(), result.getStatus().toString(), approveUrl);
        } catch (CompletionException e) {
            throw handlePaypalException(e);
        }
    }

    @Override
    public PaypalCaptureResponse captureOrder(String orderId) {
        OrdersController ordersController = paypalClient.getOrdersController();

        CaptureOrderInput captureOrderInput = new CaptureOrderInput.Builder(
                orderId, null).prefer("return=minimal").build();

        try {
            ApiResponse<Order> response = ordersController.captureOrderAsync(captureOrderInput).join();

            Order result = response.getResult();
            PurchaseUnit purchaseUnit = result.getPurchaseUnits().getFirst();
            String customId = purchaseUnit.getCustomId();
            SellerReceivableBreakdown breakdown = purchaseUnit.getPayments().getCaptures().getFirst()
                    .getSellerReceivableBreakdown();

            log.info("PayPal captured order with custom id: {} and status: {}", customId, result.getStatus().toString());

            return new PaypalCaptureResponse(
                    customId,
                    result.getStatus().toString(),
                    new BigDecimal(breakdown.getGrossAmount().getValue()),
                    new BigDecimal(breakdown.getPaypalFee().getValue()),
                    new BigDecimal(breakdown.getNetAmount().getValue())
            );
        } catch (CompletionException e) {
            throw handlePaypalException(e);
        }
    }

    private BadGatewayException handlePaypalException(Throwable exception) {
        Throwable cause = exception.getCause();

        if (cause instanceof ErrorException errorException) {
            log.error("PayPal API error: {}", errorException.getMessage());
            return new BadGatewayException("PayPal API error", "PAYPAL_API_ERROR");
        } else {
            log.error("Unexpected PayPal error: {}", exception.getMessage());
            return new BadGatewayException("Unexpected PayPal error", "PAYPAL_ERROR");
        }
    }
}
