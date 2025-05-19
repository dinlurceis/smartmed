package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.domain.OrderStatus;
import com.dinlurceis.smartmed.dto.request.OrderRequest;
import com.dinlurceis.smartmed.dto.response.OrderResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.OrderMapper;
import com.dinlurceis.smartmed.model.*;
import com.dinlurceis.smartmed.repository.*;
import com.dinlurceis.smartmed.service.OrderService;
import com.dinlurceis.smartmed.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final MedicineRepository medicineRepository;
    private final CartItemRepository cartItemRepository;
    private final AddressRepository addressRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );

        User user = userRepository.findByEmail(email);
        Cart cart = user.getCart();
        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        orderRepository.save(order);

        List<Long> itemIds = request.getItemIds();
        Set<OrderItem> orderItems = new HashSet<>();
        for (Long itemId : itemIds) {
            // tạo order item và xóa cart item
            CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(
                    () -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND)
            );
            if (cartItem.getCart().getUser() != user) {
                throw new AppException(ErrorCode.ACCESS_DENIED);
            }
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .price(cartItem.getQuantity() * cartItem.getMedicine().getPrice())
                    .medicine(cartItem.getMedicine())
                    .user(user)
                    .quantity(cartItem.getQuantity())
                    .build();
            cart.getCartItems().remove(cartItem);
            cartItemRepository.deleteById(itemId);
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
            // cập nhật số lượng thuốc mới
            Medicine medicine = medicineRepository.findById(cartItem.getMedicine().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.MEDICINE_NOT_FOUND)
            );
            if (medicine.getQuantity() < orderItem.getQuantity()) {
                throw new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);
            }
            medicine.setQuantity(medicine.getQuantity() - cartItem.getQuantity());

            medicineRepository.save(medicine);
        }

        order.setOrderItems(orderItems);
        order.setAddress(addressRepository.findById(request.getAddressId()).orElseThrow(
                () -> new AppException(ErrorCode.ADDRESS_NOT_FOUND)
        ));
        if (order.getAddress().getUser() != user) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        order.setTotalPrice(orderItems.stream().mapToDouble(
                orderItem -> orderItem.getPrice() * orderItem.getQuantity()
        ).sum());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalQuantity(orderItems.stream().mapToInt(OrderItem::getQuantity).sum());
        order = orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        );
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> findOrderByUser() {
        String email = SecurityUtils.getCurrentLogin().orElseThrow(
                () -> new AppException(ErrorCode.TOKEN_EXPIRED)
        );
        User user = userRepository.findByEmail(email);
        List<Order> orders = orderRepository.findAllByUser(user);
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getRecentOrder(int page, int size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"orderTime");
        Pageable pageable= PageRequest.of(page-1,size,sort);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.getContent().stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        );
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        );
        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new AppException(ErrorCode.CAN_NOT_CANCEL_ORDER);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
