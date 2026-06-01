package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.dto.*;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;
import com.mgrigorakis.mobiletech.repository.OrderItemRepository;
import com.mgrigorakis.mobiletech.repository.OrderRepository;
import com.mgrigorakis.mobiletech.repository.PaymentTransactionRepository;
import com.mgrigorakis.mobiletech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "analytics", key = "'total-revenue'")
    @Override
    public ValueResponse<BigDecimal> getTotalRevenue() {
        return new ValueResponse<>(paymentTransactionRepository.getTotalRevenue(PaymentStatus.PAID));
    }

    @Cacheable(value = "analytics", key = "'units-sold'")
    @Override
    public ValueResponse<Integer> getUnitsSold() {
        return orderItemRepository.getUnitsSold(PaymentStatus.PAID);
    }

    @Cacheable(value = "analytics", key = "'products-to-restock'")
    @Override
    public ValueResponse<Integer> getProductsToReStock() {
        return new ValueResponse<>(productRepository.getCountOfProductsToReStock());
    }

    @Cacheable(value = "analytics", key = "'stock-value'")
    @Override
    public ValueResponse<BigDecimal> getStockValue() {
        return new ValueResponse<>(productRepository.getStockValue());
    }

    @Cacheable(value = "analytics", key = "'revenue-by-category'")
    @Override
    public List<RevenueByCategoryResponse> getRevenueByCategory() {
        return orderItemRepository.getRevenueByCategory(PaymentStatus.PAID);
    }

    @Cacheable(value = "analytics", key = "'monthly-sales-trend'")
    @Override
    public List<MonthlySalesTrendResponse> getMonthlySalesTrend() {
        return paymentTransactionRepository.getMonthlySalesTrend(PaymentStatus.PAID);
    }

    @Cacheable(value = "analytics", key = "'top-selling-products-' + #limit")
    @Override
    public List<TopSellingProductResponse> getTopSellingProducts(int limit) {
        return orderItemRepository
                .getTopSellingProducts(PaymentStatus.PAID, PageRequest.of(0, limit));
    }

    @Cacheable(value = "analytics", key = "'orders-by-status'")
    @Override
    public List<OrderByStatusResponse> getOrdersByStatus() {
        return orderRepository.getOrderByStatus();
    }
}
