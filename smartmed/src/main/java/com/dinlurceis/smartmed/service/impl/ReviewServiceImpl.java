package com.dinlurceis.smartmed.service.impl;

import com.dinlurceis.smartmed.domain.OrderStatus;
import com.dinlurceis.smartmed.dto.request.ReviewRequest;
import com.dinlurceis.smartmed.dto.response.ReviewResponse;
import com.dinlurceis.smartmed.exception.AppException;
import com.dinlurceis.smartmed.exception.ErrorCode;
import com.dinlurceis.smartmed.mapper.ReviewMapper;
import com.dinlurceis.smartmed.model.Medicine;
import com.dinlurceis.smartmed.model.OrderItem;
import com.dinlurceis.smartmed.model.Review;
import com.dinlurceis.smartmed.model.User;
import com.dinlurceis.smartmed.repository.MedicineRepository;
import com.dinlurceis.smartmed.repository.OrderItemRepository;
import com.dinlurceis.smartmed.repository.ReviewRepository;
import com.dinlurceis.smartmed.repository.UserRepository;
import com.dinlurceis.smartmed.service.ReviewService;
import com.dinlurceis.smartmed.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewResponse> getReviewsByMedicineId(int page, int size, Long medicineId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Review> reviewPage;

        if (medicineId != null) {
            reviewPage = reviewRepository.findByMedicineId(medicineId, pageable);
        } else {
            throw new AppException(ErrorCode.MEDICINE_NOT_FOUND);
        }

        return reviewPage.stream()
                .map(reviewMapper::toReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        String email = SecurityUtils.getCurrentLogin()
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_EXPIRED));
        User user = userRepository.findByEmail(email);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }

        if (request.getRating() < 1.0 || request.getRating() > 5.0) {
            throw new AppException(ErrorCode.INVALID_RATING);
        }

        review.setRating(request.getRating());
        review.setReviewText(request.getReviewText());
        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toReviewResponse(updatedReview);
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        String email = SecurityUtils.getCurrentLogin()
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_EXPIRED));
        User user = userRepository.findByEmail(email);
        Medicine medicine = medicineRepository.findById(request.getMedicineId())
                .orElseThrow(() -> new AppException(ErrorCode.MEDICINE_NOT_FOUND));

        // tìm nếu patient từng mua medicine, tức là có order item với medicine này, status là completed
        if (!orderItemRepository.existsByUserAndMedicineIdAndOrder_orderStatus(
                user, request.getMedicineId(), OrderStatus.COMPLETED)) {
            throw new AppException(ErrorCode.CANNOT_REVIEW_MEDICINE);
        }

        // ktra rating có hợp lệ k
        if (request.getRating() < 1.0 || request.getRating() > 5.0) {
            throw new AppException(ErrorCode.INVALID_RATING);
        }

        List<OrderItem> orderItems = orderItemRepository.findByUserAndMedicineAndOrder_orderStatus(
                user, medicine, OrderStatus.COMPLETED);
        if (orderItems.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND);
        }

        // gán review cho orderItem mới nhất, vì có thể patient đã từng mua thuốc này, từng có review r, giờ review tiếp
        OrderItem latestOrderItem = orderItems.stream()
                .max(Comparator.comparing(order -> order.getOrder().getOrderTime()))
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND));

        if (latestOrderItem.getReview() != null) {
            throw new AppException(ErrorCode.ALREADY_REVIEWED);
        }

        Review review = Review.builder()
                .reviewText(request.getReviewText())
                .rating(request.getRating())
                .createdAt(LocalDateTime.now())
                .medicine(medicine)
                .user(user)
                .orderItem(latestOrderItem)
                .build();

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toReviewResponse(savedReview);
    }

    @Override
    public void deleteReview(Long id) {
        String email = SecurityUtils.getCurrentLogin()
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_EXPIRED));
        User user = userRepository.findByEmail(email);

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }

        reviewRepository.delete(review);
    }
}
