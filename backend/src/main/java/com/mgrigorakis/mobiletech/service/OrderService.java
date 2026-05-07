package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.dto.PageFilterRequest;
import com.mgrigorakis.mobiletech.common.dto.PageSortRequest;
import com.mgrigorakis.mobiletech.dto.OrderRequest;
import com.mgrigorakis.mobiletech.dto.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<OrderResponse> getOrders(PageSortRequest sort, PageFilterRequest filter);

    OrderResponse getOrderById(Long id);

    OrderResponse createOrder(OrderRequest orderRequest);
}
