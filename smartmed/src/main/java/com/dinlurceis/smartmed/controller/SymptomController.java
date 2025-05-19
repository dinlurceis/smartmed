package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.domain.BodyPart;
import com.dinlurceis.smartmed.dto.request.SymptomRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.SymptomResponse;
import com.dinlurceis.smartmed.service.SymptomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/symptom")
@RequiredArgsConstructor
public class SymptomController {
    private final SymptomService symptomService;

    @GetMapping
    public ApiResponse<PageResponse<List<SymptomResponse>>> getSymptoms(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<List<SymptomResponse>>>builder()
                .message("Get symptom list successfully")
                .result(symptomService.getSymptoms(page, size))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<SymptomResponse> getSymptomById(@PathVariable Long id) {
        return ApiResponse.<SymptomResponse>builder()
                .message("Get symptom successfully")
                .result(symptomService.getSymptomById(id))
                .build();
    }

    @GetMapping("/body-part")
    public ApiResponse<List<SymptomResponse>> getSymptomByBodyPart(@RequestParam String bodyPart) {
        return ApiResponse.<List<SymptomResponse>>builder()
                .message("Get symptom successfully")
                .result(symptomService.getSymptomByBodyPart(BodyPart.fromName(bodyPart)))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SymptomResponse> createSymptom(
            @RequestPart("request") SymptomRequest request,
            @RequestPart(required = false) MultipartFile file) {
        return ApiResponse.<SymptomResponse>builder()
                .message("Create symptom successfully")
                .result(symptomService.createSymptom(request, file))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SymptomResponse> updateSymptom(
            @PathVariable Long id,
            @RequestPart("request") SymptomRequest request,
            @RequestPart(required = false) MultipartFile file) {
        return ApiResponse.<SymptomResponse>builder()
                .message("Update symptom successfully")
                .result(symptomService.updateSymptom(id, request, file))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteSymptom(@PathVariable Long id) {
        symptomService.deleteSymptom(id);
        return ApiResponse.<Void>builder()
                .message("Delete symptom successfully")
                .build();
    }
}