package com.dinlurceis.smartmed.service;

import com.dinlurceis.smartmed.dto.request.ReviewRequest;
import com.dinlurceis.smartmed.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse getReviewById(Long reviewId);

    ReviewResponse createReview(ReviewRequest request);

    void deleteReview(Long reviewId);

    List<ReviewResponse> getReviewsByMedicineId(int page, int size, Long medicineId);

    ReviewResponse updateReview(Long reviewId, ReviewRequest request);
}
