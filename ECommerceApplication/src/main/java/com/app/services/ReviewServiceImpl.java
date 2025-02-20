package com.app.services;

import com.app.entites.Product;
import com.app.entites.Review;
import com.app.entites.User;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.ReviewDTO;
import com.app.payloads.ReviewResponse;
import com.app.repositories.ProductRepo;
import com.app.repositories.ReviewRepo;
import com.app.repositories.UserRepo;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    
        User user = userRepo.findByEmail(reviewDTO.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", reviewDTO.getUserEmail()));
        Product product = productRepo.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", reviewDTO.getProductId()));

        if (reviewRepo.findByUserAndProduct(user, product).isPresent()) {
            throw new IllegalArgumentException("User has already reviewed this product.");
        }

        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setUser(user);
        review.setProduct(product);

        Review savedReview = reviewRepo.save(review);
        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public ReviewResponse getReviewsByProduct(Long productId, int pageNumber, int pageSize) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Review> reviews = reviewRepo.findByProduct(product, pageable);

        List<ReviewDTO> content = reviews.getContent()
                .stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .toList();

        return new ReviewResponse(content, pageNumber, pageSize, reviews.getTotalElements(), reviews.getTotalPages(), reviews.isLast());
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "reviewId", reviewId));

        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());

        Review updatedReview = reviewRepo.save(review);
        return modelMapper.map(updatedReview, ReviewDTO.class);
    }

    @Override
    public String deleteReview(Long reviewId) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "reviewId", reviewId));

        reviewRepo.delete(review);
        return "Review deleted successfully";
    }
}
