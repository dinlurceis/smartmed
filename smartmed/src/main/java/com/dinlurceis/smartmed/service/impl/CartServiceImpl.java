package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.dto.request.CartItemRequest;
import com.dinlurceis.smartmed.dto.response.CartItemResponse;
import com.dinlurceis.smartmed.dto.response.CartTotalResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.CartItemMapper;
import com.dinlurceis.smartmed.mapper.CartMapper;
import com.dinlurceis.smartmed.model.*;
import com.dinlurceis.smartmed.repository.*;
import com.dinlurceis.smartmed.service.CartService;
import com.dinlurceis.smartmed.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;

    private final UserRepository userRepository;

    private final MedicineRepository medicineRepository;

    private final AppointmentRepository appointmentRepository;


    @Override
    public CartTotalResponse addOrUpdateItemCart(CartItemRequest request) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);
        Cart cart = user.getCart();
        if (cart == null) {
            cart = Cart.builder()
                    .user(user)
                    .build();
            user.setCart(cart);
        }

        CartItem existingItem = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getMedicine().getId().equals(request.getMedicineId()))
                .findFirst()
                .orElse(null);
        log.info("1");
        if (existingItem == null) {
            CartItem newCartItem = cartItemMapper.toCartItem(request);
            newCartItem.setMedicine(medicineRepository.findById(request.getMedicineId()).orElseThrow(
                    () -> new AppException(ErrorCode.MEDICINE_NOT_FOUND)
            ));
            log.info("2");
            newCartItem.setCart(cart);
            cart.getCartItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
        }
        else {
            existingItem.setQuantity(request.getQuantity());
            cartItemRepository.save(existingItem);
        }
        return CartTotalResponse.builder()
                .cartItems(cart.getCartItems().stream().map(item -> CartItemResponse.builder()
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .medicineId(item.getMedicine().getId())
                                .build()).collect(Collectors.toSet()))
                .totalPrice(cart.getTotalPrice())
                .totalQuantity(cart.getTotalQuantity())
                .id(cart.getId())
                .build();
    }

    @Override
    public CartTotalResponse deleteCartItem(Long cartItemId) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);
        Cart cart = user.getCart();
        CartItem existingItem = cart.getCartItems().stream().filter(cartItem ->
                cartItem.getId().equals(cartItemId)).findFirst().orElseThrow(
                () -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND)
        );

        cart.getCartItems().remove(existingItem);
        cartItemRepository.deleteById(cartItemId);

        return CartTotalResponse.builder()
                .cartItems(cart.getCartItems().stream().map(item -> CartItemResponse.builder()
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .medicineId(item.getMedicine().getId())
                        .build()).collect(Collectors.toSet()))
                .totalPrice(cart.getTotalPrice())
                .totalQuantity(cart.getTotalQuantity())
                .id(cart.getId())
                .build();
    }

    @Override
    public void deleteAllCartItems() {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);
        Cart cart = user.getCart();
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartTotalResponse getCart() {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);
        Cart cart = user.getCart();

        return CartTotalResponse.builder()
                .cartItems(cart.getCartItems().stream().map(item -> CartItemResponse.builder()
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .medicineId(item.getMedicine().getId())
                        .build()).collect(Collectors.toSet()))
                .totalPrice(cart.getTotalPrice())
                .totalQuantity(cart.getTotalQuantity())
                .id(cart.getId())
                .build();
    }

    @Override
    public CartTotalResponse addToCartFromAppointment(Long appointmentId) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(() -> new AppException(ErrorCode.TOKEN_EXPIRED));
        Cart cart = userRepository.findByEmail(email).getCart();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        if (!appointment.getPatient().getEmail().equals(email)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        Set<Medicine> medicines = appointment.getMedicines();
        for (Medicine medicine : medicines) {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .medicine(medicine)
                    .quantity(1)
                    .build();
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);

        return CartTotalResponse.builder()
                .cartItems(cart.getCartItems().stream().map(item -> CartItemResponse.builder()
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .medicineId(item.getMedicine().getId())
                        .build()).collect(Collectors.toSet()))
                .totalPrice(cart.getTotalPrice())
                .totalQuantity(cart.getTotalQuantity())
                .id(cart.getId())
                .build();
    }
}
