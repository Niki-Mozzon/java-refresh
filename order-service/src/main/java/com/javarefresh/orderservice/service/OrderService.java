package com.javarefresh.orderservice.service;

import com.javarefresh.orderservice.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    OrderRequest createOrder(OrderRequest orderRequest);

    OrderRequest getOrderById(Long id);

    List<OrderRequest> getAllOrders();
}
