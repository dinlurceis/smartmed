package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.response.CartTotalResponse;
import com.dinlurceis.smartmed.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartTotalResponse toCartTotalResponse(Cart cart);
}
