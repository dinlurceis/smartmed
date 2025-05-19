package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.domain.OrderStatus;
import com.dinlurceis.smartmed.dto.request.OrderRequest;
import com.dinlurceis.smartmed.dto.request.OrderRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.OrderResponse;
import com.dinlurceis.smartmed.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    public final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .message("Create order successfully")
                .result(orderService.createOrder(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('PATIENT')")
    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .message("Get order successfully")
                .result(orderService.getOrder(id))
                .build();
    }


    @GetMapping("/history")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<List<OrderResponse>> getHistoryOrder() {
        return ApiResponse.<List<OrderResponse>>builder()
                .message("All history order")
                .result(orderService.findOrderByUser())
                .build();
    }

    @GetMapping("/recent")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<OrderResponse>> getRecentOrder(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        return ApiResponse.<List<OrderResponse>>builder()
                .message("List order by createdAt by desc")
                .result(orderService.getRecentOrder(page, size))
                .build();
    }

    @PatchMapping("/status/{orderId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String newStatus
    ) {
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(newStatus.toUpperCase()));
        return ApiResponse.<Void>builder()
                .message("change orderStatus successfully")
                .build();
    }

    @PatchMapping("/cancel/{orderId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    public ApiResponse<?> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ApiResponse.<Void>builder()
                .message("Cancel order successfully")
                .build();
    }
}
