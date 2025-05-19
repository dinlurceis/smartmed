package com.dinlurceis.smartmed.mapper;

import com.dinlurceis.smartmed.dto.request.ReviewRequest;
import com.dinlurceis.smartmed.dto.response.ReviewResponse;
import com.dinlurceis.smartmed.model.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toReview(ReviewRequest request);
    ReviewResponse toReviewResponse(Review review);
}
