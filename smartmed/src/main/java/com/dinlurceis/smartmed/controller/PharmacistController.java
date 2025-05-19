package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.PharmacistRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.PharmacistResponse;
import com.dinlurceis.smartmed.service.PharmacistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacist")
@RequiredArgsConstructor
public class PharmacistController {
    private final PharmacistService pharmacistService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PageResponse<List<PharmacistResponse>>> getPharmacists(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<List<PharmacistResponse>>>builder()
                .message("Get pharmacist list successfully")
                .result(pharmacistService.getPharmacists(page, size))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PharmacistResponse> getPharmacistById(@PathVariable Long id) {
        return ApiResponse.<PharmacistResponse>builder()
                .message("Get pharmacist successfully")
                .result(pharmacistService.getPharmacistById(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PharmacistResponse> createPharmacist(@RequestBody PharmacistRequest request) {
        return ApiResponse.<PharmacistResponse>builder()
                .message("Create pharmacist successfully")
                .result(pharmacistService.createPharmacist(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PharmacistResponse> updatePharmacist(
            @PathVariable Long id,
            @Valid @RequestBody PharmacistRequest request) {
        return ApiResponse.<PharmacistResponse>builder()
                .message("Update pharmacist successfully")
                .result(pharmacistService.updatePharmacist(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePharmacist(@PathVariable Long id) {
        pharmacistService.deletePharmacist(id);
        return ApiResponse.<Void>builder()
                .message("Delete pharmacist successfully")
                .build();
    }
}