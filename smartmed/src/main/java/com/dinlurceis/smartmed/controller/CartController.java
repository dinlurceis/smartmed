package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.CartItemRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.CartTotalResponse;
import com.dinlurceis.smartmed.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    @PostMapping("/add-to-cart")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<CartTotalResponse> addItemCart(
            @RequestBody CartItemRequest request) {
        return ApiResponse.<CartTotalResponse>builder()
                .message("Add or update cart Successfully")
                .result(cartService.addOrUpdateItemCart(request))
                .build();
    }
    @DeleteMapping("/delete/{cartItemId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<CartTotalResponse> deleteCartItem(
            @PathVariable Long cartItemId) {
        return ApiResponse.<CartTotalResponse>builder()
                .message("Delete cart item successfully")
                .result(cartService.deleteCartItem(cartItemId))
                .build();
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<?> deleteAllCartItem() {
        cartService.deleteAllCartItems();
        return ApiResponse.<Void>builder()
                .message("Delete all cart items successfully")
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<CartTotalResponse> getCart() {
        return ApiResponse.<CartTotalResponse>builder()
                .message("Get cart successfully")
                .result(cartService.getCart())
                .build();
    }

    @PostMapping("/add-appointment")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<CartTotalResponse> addAppointment(@RequestParam Long appointmentId) {
        return ApiResponse.<CartTotalResponse>builder()
                .message("Add to cart successfully")
                .result(cartService.addToCartFromAppointment(appointmentId))
                .build();
    }
}
