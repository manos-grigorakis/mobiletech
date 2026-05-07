package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.dto.PageFilterRequest;
import com.mgrigorakis.mobiletech.common.dto.PageSortRequest;
import com.mgrigorakis.mobiletech.common.exception.ResourceNotFoundException;
import com.mgrigorakis.mobiletech.dto.OrderItemSummaryResponse;
import com.mgrigorakis.mobiletech.dto.OrderRequest;
import com.mgrigorakis.mobiletech.dto.OrderResponse;
import com.mgrigorakis.mobiletech.dto.PaymentTransactionResponse;
import com.mgrigorakis.mobiletech.mapper.OrderMapper;
import com.mgrigorakis.mobiletech.model.Order;
import com.mgrigorakis.mobiletech.model.OrderItem;
import com.mgrigorakis.mobiletech.model.Product;
import com.mgrigorakis.mobiletech.model.enums.OrderStatus;
import com.mgrigorakis.mobiletech.repository.OrderRepository;
import com.mgrigorakis.mobiletech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Page<OrderResponse> getOrders(PageSortRequest sort, PageFilterRequest filter) {
        Pageable pageable = PageRequest.of(filter.page(), filter.size(), sort.createSort());
        Page<Order> orders = orderRepository.findAll(pageable);

        return orders.map(order -> {
            List<OrderItemSummaryResponse> orderItems = order.getOrderItems()
                    .stream()
                    .map(OrderMapper::toResponse)
                    .toList();

            List<PaymentTransactionResponse> paymentTransactions = order.getPaymentTransactions()
                    .stream()
                    .map(OrderMapper::toResponse)
                    .toList();

            return OrderMapper.toResponse(order, orderItems, paymentTransactions);
        });
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
           log.warn("No order found with id {}", id);
           return new ResourceNotFoundException("No order found with id " + id);
        });

        List<OrderItemSummaryResponse> orderItemSummaryResponse = order.getOrderItems().stream().map(
                OrderMapper::toResponse).toList();

        List<PaymentTransactionResponse> paymentTransactions = order.getPaymentTransactions().stream().map(
                OrderMapper::toResponse).toList();

        return OrderMapper.toResponse(order, orderItemSummaryResponse, paymentTransactions);
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        List<OrderItem> orderItems = orderRequest.orderItems().stream().map(orderItemRequest -> {
            Product product = productRepository.findById(orderItemRequest.productId()).orElseThrow(() -> {
                log.warn("Product not found with id {}", orderItemRequest.productId());
                return new ResourceNotFoundException("Product not found with id " + orderItemRequest.productId());
            });
            return OrderMapper.toEntity(orderItemRequest, product);
        }).toList();

        Order order = OrderMapper.toEntity(orderRequest, orderItems);
        order.calculateTotalAmount();
        order.setOrderStatus(OrderStatus.PROCESSING);
        Order savedOrder = orderRepository.save(order);

        List<OrderItemSummaryResponse> orderItemSummaryResponse = orderItems.stream().map(
                OrderMapper::toResponse).toList();
        return OrderMapper.toResponse(savedOrder, orderItemSummaryResponse, List.of());
    }
}
