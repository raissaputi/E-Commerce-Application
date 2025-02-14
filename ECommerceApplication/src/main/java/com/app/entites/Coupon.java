package com.app.entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private Double discountAmount;

    @Column(nullable = false)
    private Boolean active = true;
}