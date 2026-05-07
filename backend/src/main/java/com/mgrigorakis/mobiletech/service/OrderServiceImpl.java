package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.exception.ResourceNotFoundException;
import com.mgrigorakis.mobiletech.dto.OrderItemSummaryResponse;
import com.mgrigorakis.mobiletech.dto.OrderRequest;
import com.mgrigorakis.mobiletech.dto.OrderResponse;
import com.mgrigorakis.mobiletech.mapper.OrderMapper;
import com.mgrigorakis.mobiletech.model.Order;
import com.mgrigorakis.mobiletech.model.OrderItem;
import com.mgrigorakis.mobiletech.model.Product;
import com.mgrigorakis.mobiletech.model.enums.OrderStatus;
import com.mgrigorakis.mobiletech.repository.OrderRepository;
import com.mgrigorakis.mobiletech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

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
