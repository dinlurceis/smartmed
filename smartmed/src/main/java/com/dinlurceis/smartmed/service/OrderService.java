package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.domain.OrderStatus;
import com.dinlurceis.smartmed.dto.request.OrderRequest;
import com.dinlurceis.smartmed.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrder(Long id);

    List<OrderResponse> findOrderByUser();

    List<OrderResponse> getRecentOrder(int page, int size);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);

    void cancelOrder(Long orderId);
}
