package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.OrderRequest;
import com.dinlurceis.smartmed.dto.response.OrderResponse;
import com.dinlurceis.smartmed.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Order order);
    Order toOrder(OrderRequest request);
}
