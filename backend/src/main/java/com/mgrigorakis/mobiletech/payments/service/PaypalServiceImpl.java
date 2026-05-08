package com.mgrigorakis.mobiletech.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgrigorakis.mobiletech.common.exception.BadGatewayException;
import com.mgrigorakis.mobiletech.dto.OrderResponse;
import com.mgrigorakis.mobiletech.dto.OrderStatusUpdateRequest;
import com.mgrigorakis.mobiletech.model.PaymentTransaction;
import com.mgrigorakis.mobiletech.model.enums.OrderStatus;
import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;
import com.mgrigorakis.mobiletech.payments.dto.CreatePaymentRequest;
import com.mgrigorakis.mobiletech.payments.dto.PaypalOrderResponse;
import com.mgrigorakis.mobiletech.service.OrderService;
import com.mgrigorakis.mobiletech.service.PaymentTransactionService;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ErrorException;
import com.paypal.sdk.http.response.ApiResponse;
import com.paypal.sdk.models.*;
import jakarta.transaction.Transactional;
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
public class PaypalServiceImpl implements PaymentProvider {
    private final PaypalServerSdkClient paypalClient;
    private final PaymentTransactionService paymentTransactionService;
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @Value("${app.payments.paypal.redirect-url}")
    private String paypalReturnUrl;

    @Value("${app.payments.paypal.cancel-url}")
    private String paypalCancelUrl;

   @Value("${app.payments.paypal.checkout-success-url}")
   private String paypalCheckoutSuccessUrl;

   @Value("${app.payments.paypal.checkout-error-url}")
   private String paypalCheckoutErrorUrl;

    @Override
    public PaymentProviderType getType() {
        return PaymentProviderType.PAYPAL;
    }

    @Override
    public PaypalOrderResponse createPayment(CreatePaymentRequest request) {
        OrderResponse order = orderService.getOrderById(request.orderId());

        OrdersController ordersController = paypalClient.getOrdersController();

        AmountWithBreakdown amount = new AmountWithBreakdown
                .Builder("EUR", order.totalAmount().toPlainString()).build();

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

            log.info("PayPal order created for orderId: {} paypalOrderId: {}", order.id(), result.getId());
            return new PaypalOrderResponse(result.getId(), result.getStatus().toString(), approveUrl);
        } catch (CompletionException e) {
            throw handlePaypalException(e);
        }
    }

    @Transactional
    @Override
    public void handleWebhook(String payload, String signature) {
        try {
            JsonNode root = objectMapper.readTree(payload);
            String eventType = root.get("event_type").asText();

            switch (eventType) {
                case "PAYMENT.CAPTURE.COMPLETED":
                    JsonNode resource = root.get("resource");
                    processCompletedOrder(resource);
                    break;
                default:
                    log.info("Unhandled PayPal event type: {}", eventType);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void processCompletedOrder(JsonNode resource) {
        String customId = resource.get("custom_id").asText();
        JsonNode sellerBreakdown = resource.get("seller_receivable_breakdown");
        BigDecimal grossAmount = new BigDecimal(sellerBreakdown.get("gross_amount").get("value").asText());
        BigDecimal providerFee = new BigDecimal(sellerBreakdown.get("paypal_fee").get("value").asText());
        BigDecimal netAmount = new BigDecimal(sellerBreakdown.get("net_amount").get("value").asText());

        PaymentTransaction transaction = PaymentTransaction.builder()
                .paymentProvider(PaymentProviderType.PAYPAL)
                .paymentStatus(PaymentStatus.PAID)
                .grossAmount(grossAmount)
                .providerFeeAmount(providerFee)
                .netAmount(netAmount)
                .build();

        transaction.setProviderTransactionId(resource.get("id").asText());

        paymentTransactionService.createPaymentTransaction(Long.parseLong(customId), transaction);
        orderService.updateOrderStatusById(
                Long.parseLong(customId), new OrderStatusUpdateRequest(OrderStatus.CONFIRMED));
    }

    @Override
    public String completePayment(String identifier) {
        try {
            OrdersController orderController = paypalClient.getOrdersController();
            CaptureOrderInput input = new CaptureOrderInput.Builder(identifier, null).build();
            orderController.captureOrderAsync(input).join();

            log.info("PayPal order capture completed for token: {}", identifier);
            return paypalCheckoutSuccessUrl;
        } catch (CompletionException e) {
            log.error("PayPal order capture failed for token: {}", identifier, e);
            return paypalCheckoutErrorUrl;
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
