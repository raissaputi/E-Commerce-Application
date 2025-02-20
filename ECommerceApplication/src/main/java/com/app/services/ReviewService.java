package com.app.services;

import com.app.payloads.ReviewDTO;
import com.app.payloads.ReviewResponse;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewResponse getReviewsByProduct(Long productId, int pageNumber, int pageSize);
    ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO);
    String deleteReview(Long reviewId);
}
