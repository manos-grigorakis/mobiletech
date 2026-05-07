package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.dto.OrderRequest;
import com.mgrigorakis.mobiletech.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
}
