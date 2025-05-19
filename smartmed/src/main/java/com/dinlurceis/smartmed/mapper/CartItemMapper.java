package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.CartItemRequest;
import com.dinlurceis.smartmed.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toCartItem(CartItemRequest request);
}
