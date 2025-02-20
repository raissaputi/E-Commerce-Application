package com.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private String comment;
    private int rating;
    private String userEmail;
    private Long productId;
}
