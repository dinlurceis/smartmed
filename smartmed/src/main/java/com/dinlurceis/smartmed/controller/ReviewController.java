package com.dinlurceis.smartmed.controller;

import com.dinlurceis.smartmed.dto.request.ReviewRequest;
import com.dinlurceis.smartmed.dto.response.ApiResponse;
import com.dinlurceis.smartmed.dto.response.PageResponse;
import com.dinlurceis.smartmed.dto.response.ReviewResponse;
import com.dinlurceis.smartmed.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ApiResponse<PageResponse<List<ReviewResponse>>> getReviewsByMedicineId(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam Long medicineId
    ) {
        return ApiResponse.<PageResponse<List<ReviewResponse>>>builder()
                .message("Get reviews successfully")
                .result(PageResponse.<List<ReviewResponse>>builder()
                        .currentPage(page)
                        .items(reviewService.getReviewsByMedicineId(page, size, medicineId))
                        .pageSize(size)
                        .totalPages(10)
                        .totalElements(100L)
                        .build())
                .build();
    }

    @GetMapping("/{reviewId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<ReviewResponse> getReview(@PathVariable Long reviewId) {
        return ApiResponse.<ReviewResponse>builder()
                .message("Get review successfully")
                .result(reviewService.getReviewById(reviewId))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<ReviewResponse> createReview(@RequestBody ReviewRequest request) {
        return ApiResponse.<ReviewResponse>builder()
                .message("Create review successfully")
                .result(reviewService.createReview(request))
                .build();
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.<Void>builder()
                .message("Delete review successfully")
                .build();
    }

    @PutMapping("/update/{reviewId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ApiResponse<ReviewResponse> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest request) {
        return ApiResponse.<ReviewResponse>builder()
                .message("Update review successfully")
                .result(reviewService.updateReview(reviewId, request))
                .build();
    }
}

