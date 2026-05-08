package com.mgrigorakis.mobiletech.payments.service;

import com.mgrigorakis.mobiletech.common.exception.BadGatewayException;
import com.mgrigorakis.mobiletech.dto.OrderResponse;
import com.mgrigorakis.mobiletech.dto.OrderStatusUpdateRequest;
import com.mgrigorakis.mobiletech.model.Order;
import com.mgrigorakis.mobiletech.model.PaymentTransaction;
import com.mgrigorakis.mobiletech.model.enums.OrderStatus;
import com.mgrigorakis.mobiletech.model.enums.PaymentProvider;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;
import com.mgrigorakis.mobiletech.payments.dto.StripePaymentIntentRequest;
import com.mgrigorakis.mobiletech.payments.dto.StripePaymentIntentResponse;
import com.mgrigorakis.mobiletech.service.OrderService;
import com.mgrigorakis.mobiletech.service.PaymentTransactionService;
import com.stripe.StripeClient;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class StripeServiceImpl {
    private final StripeClient stripeClient;
    private final OrderService orderService;
    private final PaymentTransactionService paymentTransactionService;

    @Value("${app.payments.stripe.webhook-secret}")
    private String webhookSecret;

    public StripePaymentIntentResponse createStripePaymentIntent(StripePaymentIntentRequest request) {
        OrderResponse order = orderService.getOrderById(request.orderId());

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(order.totalAmount().multiply(BigDecimal.valueOf(100)).longValue()) // Convert to cents
                .setCurrency("eur")
                .putAllMetadata(Map.of(
                        "orderId", order.id().toString(),
                        "itemCount", String.valueOf(order.orderItems().size())
                ))
                .setReceiptEmail(order.email())
                .setShipping(buildShipping(order))
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();

        try {
            PaymentIntent paymentIntent = stripeClient.v1().paymentIntents().create(params);

            log.info("Stripe payment succeeded for orderId: {} paymentIntentId: {}", order.id(), paymentIntent.getId());
            return new StripePaymentIntentResponse(paymentIntent.getClientSecret(),  paymentIntent.getId());
        } catch (StripeException e) {
            log.error("Stripe error for request: {}", request, e);
            throw new BadGatewayException("Stripe API error", "STRIPE_ERROR");
        }
    }

    @Transactional
    public void handleWebhook(String request, String sigHeader) {
        try {
            if(request != null && !request.isEmpty() && sigHeader != null && !sigHeader.isEmpty()) {
                Event event = stripeClient.constructEvent(request, sigHeader, webhookSecret);
                handlePayment(event);
            }
        } catch (SignatureVerificationException e) {
            log.error("Signature verification failed for request: {}", request, e);
            throw new BadGatewayException("Signature verification failed", "SIGNATURE_VERIFICATION_FAILED");
        } catch (StripeException e) {
            log.error("Stripe error for request: {}", request, e);
            throw new BadGatewayException("Stripe API error", "STRIPE_ERROR");
        }
    }

    /**
     * Handles the events from Stripe Webhook
     * <ul>
     *     <li>Event {@code payment_intent.succeeded} - uses {@link #handlePaymentIntentSucceeded(PaymentIntent)}</li>
     * </ul>
     * @param event The event from Stripe
     * @throws StripeException If a Stripe API call fails
     * @throw {@link BadGatewayException} If deserialization of {@link StripeObject} fails
     */
    private void handlePayment(Event event) throws StripeException {
        StripeObject stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(() -> {
            log.warn("Failed to deserialize Stripe object for event {}", event);
            return new BadGatewayException("Failed to deserialize Stripe object for event", "STRIPE_EVENT_ERROR");
        });

        switch (event.getType()) {
            case "payment_intent.succeeded":
                handlePaymentIntentSucceeded((PaymentIntent) stripeObject);
                break;
            default:
                log.warn("Unhandled event type: {}", event.getType());
        }
    }

    /**
     * Handles the {@code payment_intent.succeeded} event from Stripe webhook
     * <ul>
     *     <li>Retrieves the {@link BalanceTransaction} from Stripe to extract gross, fee and net amounts</li>
     *     <li>Persists a new {@link PaymentTransaction} with {@link PaymentStatus#PAID}</li>
     *     <li>Updates the {@link Order#orderStatus} to {@link OrderStatus#CONFIRMED}</li>
     * </ul>
     * @param paymentIntent The {@link PaymentIntent} from the Stripe event
     * @throws StripeException If a Stripe API call fails
     */
    private void handlePaymentIntentSucceeded(PaymentIntent paymentIntent) throws StripeException {
        String orderId = paymentIntent.getMetadata().get("orderId");

        if(orderId == null) {
            log.warn("Missing orderId in Stripe Payment Intent {}", paymentIntent.getId());
            return;
        }

        String balanceTxId = paymentIntent.getLatestCharge();
        Charge charge = stripeClient.v1().charges().retrieve(balanceTxId);
        BalanceTransaction balanceTx = stripeClient.v1().balanceTransactions().
                retrieve(charge.getBalanceTransaction());

        // Values in cents
        long fee = balanceTx.getFee();
        long gross = balanceTx.getAmount();
        long net = balanceTx.getNet();

        PaymentTransaction transaction = PaymentTransaction.builder()
                .paymentProvider(PaymentProvider.STRIPE)
                .paymentStatus(PaymentStatus.PAID)
                .grossAmount(centsToEuro(gross))
                .providerFeeAmount(centsToEuro(fee))
                .netAmount(centsToEuro(net))
                .build();

        paymentTransactionService.createPaymentTransaction(Long.parseLong(orderId), transaction);
        orderService.updateOrderStatusById(Long.valueOf(orderId), new OrderStatusUpdateRequest(OrderStatus.CONFIRMED));
    }

    /**
     * Converts cents to euro
     * @param cents The amount in cents to be converted in Euro
     * @return The converted value in Euro
     */
    private BigDecimal centsToEuro(Long cents) {
        return BigDecimal.valueOf(cents).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /**
     * Builds an order shipping address for Stripe payment
     * @param order The actual order
     * @return The built shipping address
     */
    private PaymentIntentCreateParams.Shipping.Address buildAddress(OrderResponse order) {
        return PaymentIntentCreateParams.Shipping.Address.builder()
                .setCity(order.city())
                .setCountry(order.country())
                .setLine1(order.address())
                .setPostalCode(order.postalCode())
                .build();
    }

    /**
     * Builds an order shipping for Stripe payment
     * <p>Uses {@link #buildAddress(OrderResponse)} to build shipping address</p>
     * @param order The actual order
     * @return The built shipping
     */
    private PaymentIntentCreateParams.Shipping buildShipping(OrderResponse order) {
        return PaymentIntentCreateParams.Shipping.builder()
                .setAddress(buildAddress(order))
                .setName(order.getFullName())
                .setPhone(order.phone())
                .build();
    }
}
