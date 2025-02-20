package com.app.controllers;

import com.app.payloads.ReviewDTO;
import com.app.payloads.ReviewResponse;
import com.app.services.ReviewService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@SecurityRequirement(name = "E-Commerce Application")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        System.out.println("************************************************************************");
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ReviewResponse> getReviewsByProduct(@PathVariable Long productId,
                                                              @RequestParam(defaultValue = "0") int pageNumber,
                                                              @RequestParam(defaultValue = "10") int pageSize) {
        ReviewResponse response = reviewService.getReviewsByProduct(productId, pageNumber, pageSize);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        String message = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(message);
    }
}
