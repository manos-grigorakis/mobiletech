package com.mgrigorakis.mobiletech.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.dto.*;
import com.mgrigorakis.mobiletech.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/analytics")
@RestController
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/total-revenue")
    public ApiResponse<ValueResponse<BigDecimal>> getTotalRevenue() {
        return new ApiResponse<>(analyticsService.getTotalRevenue());
    }

    @GetMapping("/units-sold")
    public ApiResponse<ValueResponse<Integer>> getUnitsSold() {
        return new ApiResponse<>(analyticsService.getUnitsSold());
    }

    @GetMapping("/products-re-stock")
    public ApiResponse<ValueResponse<Integer>> getProductsToReStock() {
        return new ApiResponse<>(analyticsService.getProductsToReStock());
    }

    @GetMapping("/stock-value")
    public ApiResponse<ValueResponse<BigDecimal>> getStockValue() {
        return new ApiResponse<>(analyticsService.getStockValue());
    }

    @GetMapping("/revenue-by-category")
    public ApiResponse<List<RevenueByCategoryResponse>> getRevenueByCategory() {
        return new ApiResponse<>(analyticsService.getRevenueByCategory());
    }

    @GetMapping("/monthly-sales-trend")
    public ApiResponse<List<MonthlySalesTrendResponse>> getMonthlySalesTrend() {
        return new ApiResponse<>(analyticsService.getMonthlySalesTrend());
    }

    @GetMapping("/top-selling-products")
    public ApiResponse<List<TopSellingProductResponse>> getTopSellingProducts(
            @RequestParam(defaultValue = "5") int limit) {
        return new ApiResponse<>(analyticsService.getTopSellingProducts(limit));
    }

    @GetMapping("/orders-by-status")
    public ApiResponse<List<OrderByStatusResponse>> getOrdersByStatus() {
        return new ApiResponse<>(analyticsService.getOrdersByStatus());
    }
}
