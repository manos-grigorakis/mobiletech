package com.mgrigorakis.mobiletech.mapper;

import com.mgrigorakis.mobiletech.dto.*;
import com.mgrigorakis.mobiletech.model.Order;
import com.mgrigorakis.mobiletech.model.OrderItem;
import com.mgrigorakis.mobiletech.model.PaymentTransaction;
import com.mgrigorakis.mobiletech.model.Product;

import java.util.List;

public class OrderMapper {
    // Request -> Entity
    public static Order toEntity(OrderRequest orderRequest, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .firstName(orderRequest.firstName())
                .lastName(orderRequest.lastName())
                .email(orderRequest.email())
                .phone(orderRequest.phone())
                .address(orderRequest.address())
                .city(orderRequest.city())
                .postalCode(orderRequest.postalCode())
                .country(orderRequest.country())
                .build();

        orderItems.forEach(order::addOrderItem);

        return order;
    }

    public static OrderItem toEntity(OrderItemRequest orderItemRequest, Product product) {
        return OrderItem.builder()
                .price(product.getPrice())
                .quantity(orderItemRequest.quantity())
                .product(product)
                .build();
    }

    // Entity -> Response
    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderStatus(), order.getFirstName(), order.getLastName(),
                                 order.getEmail(), order.getPhone(), order.getAddress(), order.getCity(),
                                 order.getPostalCode(), order.getCountry(), order.getTotalAmount(),
                                 order.getCreatedAt(),
                                 order.getUpdatedAt(), toOrderItemSummaryResponse(order.getOrderItems()),
                                 toPaymentTransactionResponse(order.getPaymentTransactions()));
    }

    private static List<OrderItemSummaryResponse> toOrderItemSummaryResponse(List<OrderItem> orderItems) {
        return orderItems.stream().map(
                orderItem -> new OrderItemSummaryResponse(orderItem.getId(), orderItem.getPrice(),
                                                          orderItem.getQuantity(),
                                                          orderItem.getProduct().getId())).toList();
    }

    private static List<PaymentTransactionResponse> toPaymentTransactionResponse(
            List<PaymentTransaction> paymentTransactions) {
        return paymentTransactions.stream().map(transaction -> new PaymentTransactionResponse(
                transaction.getId(), transaction.getPaymentProvider(), transaction.getPaymentStatus(),
                transaction.getGrossAmount(), transaction.getProviderFeeAmount(), transaction.getNetAmount())).toList();
    }
}
