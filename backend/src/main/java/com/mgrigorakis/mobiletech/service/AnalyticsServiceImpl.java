package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.dto.*;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;
import com.mgrigorakis.mobiletech.repository.OrderItemRepository;
import com.mgrigorakis.mobiletech.repository.OrderRepository;
import com.mgrigorakis.mobiletech.repository.PaymentTransactionRepository;
import com.mgrigorakis.mobiletech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Override
    public ValueResponse<BigDecimal> getTotalRevenue() {
        return new ValueResponse<>(paymentTransactionRepository.getTotalRevenue(PaymentStatus.PAID));
    }

    @Override
    public ValueResponse<Integer> getUnitsSold() {
        return orderItemRepository.getUnitsSold(PaymentStatus.PAID);
    }

    @Override
    public ValueResponse<Integer> getProductsToReStock() {
        return new ValueResponse<>(productRepository.getCountOfProductsToReStock());
    }

    @Override
    public ValueResponse<BigDecimal> getStockValue() {
        return new ValueResponse<>(productRepository.getStockValue());
    }

    @Override
    public List<RevenueByCategoryResponse> getRevenueByCategory() {
        return orderItemRepository.getRevenueByCategory(PaymentStatus.PAID);
    }

    @Override
    public List<MonthlySalesTrendResponse> getMonthlySalesTrend() {
        return paymentTransactionRepository.getMonthlySalesTrend(PaymentStatus.PAID);
    }

    @Override
    public List<TopSellingProductResponse> getTopSellingProducts(int limit) {
        return orderItemRepository
                .getTopSellingProducts(PaymentStatus.PAID, PageRequest.of(0, limit));
    }

    @Override
    public List<OrderByStatusResponse> getOrdersByStatus() {
        return orderRepository.getOrderByStatus();
    }
}
