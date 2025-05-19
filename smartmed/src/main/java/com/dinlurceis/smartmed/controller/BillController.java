package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.BillRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.BillResponse;
import com.dinlurceis.smartmed.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<BillResponse>> getBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<List<BillResponse>>builder()
                .message("Get bill list successfully")
                .result(billService.getBills(page, size)).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BillResponse> getBillById(@PathVariable Long id) {
        return ApiResponse.<BillResponse>builder()
                .message("Get bill successfully")
                .result(billService.getBillById(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BillResponse> createBill(@RequestBody BillRequest request) {
        return ApiResponse.<BillResponse>builder()
                .result(billService.createBill(request))
                .message("Create bill successfully")
                .build();
    }
}