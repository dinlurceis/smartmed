package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.CartItemRequest;
import com.dinlurceis.smartmed.dto.response.CartTotalResponse;

public interface CartService {
    CartTotalResponse addOrUpdateItemCart(CartItemRequest request);

    CartTotalResponse deleteCartItem(Long cartItemId);

    void deleteAllCartItems();

    CartTotalResponse getCart();

    CartTotalResponse addToCartFromAppointment(Long appointmentId);
}
