package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface AnalyticsService {
    ValueResponse<BigDecimal> getTotalRevenue();

    ValueResponse<Integer> getUnitsSold();

    ValueResponse<Integer> getProductsToReStock();

    ValueResponse<BigDecimal> getStockValue();

    List<RevenueByCategoryResponse> getRevenueByCategory();

    List<MonthlySalesTrendResponse> getMonthlySalesTrend();

    List<TopSellingProductResponse> getTopSellingProducts(int limit);

    List<OrderByStatusResponse> getOrdersByStatus();
}
