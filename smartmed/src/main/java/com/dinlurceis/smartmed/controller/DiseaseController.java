package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.DiseaseRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.DiseaseResponse;
import com.dinlurceis.smartmed.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/disease")
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;

    @GetMapping
    @PreAuthorize( "hasRole('ADMIN')")
    public ApiResponse<List<DiseaseResponse>> getDiseases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<List<DiseaseResponse>>builder()
                .message("Get disease list successfully")
                .result(diseaseService.getDiseases(page, size))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize( "isAuthenticated()")
    public ApiResponse<DiseaseResponse> getDiseaseById(@PathVariable Long id) {
        return ApiResponse.<DiseaseResponse>builder()
                .message("Get disease successfully")
                .result(diseaseService.getDiseaseById(id))
                .build();
    }

    @PostMapping
    @PreAuthorize( "hasRole('ADMIN')")
    public ApiResponse<DiseaseResponse> createDisease(
            @RequestPart("request") DiseaseRequest request,
            @RequestPart(required = false) MultipartFile file) {
        return ApiResponse.<DiseaseResponse>builder()
                .message("Create disease successfully")
                .result(diseaseService.createDisease(request, file))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')")
    public ApiResponse<DiseaseResponse> updateDisease(
            @PathVariable Long id,
            @RequestPart("request") DiseaseRequest request,
            @RequestPart(required = false) MultipartFile file) {
        return ApiResponse.<DiseaseResponse>builder()
                .message("Update disease successfully")
                .result(diseaseService.updateDisease(id, request, file))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')")
    public ApiResponse<Void> deleteDisease(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
        return ApiResponse.<Void>builder()
                .message("Delete disease successfully")
                .build();
    }
}