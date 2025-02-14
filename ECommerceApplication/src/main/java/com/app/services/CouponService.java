package com.app.services;

import com.app.payloads.CouponDTO;
import com.app.payloads.CouponResponse;

public interface  CouponService {
    CouponDTO createCoupon(CouponDTO couponDTO);

    CouponResponse getAllCoupons(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CouponDTO updateCoupon(Long couponId, CouponDTO couponDTO);
    
    CouponDTO getCouponByCode(String code);

    String deleteCoupon(Long couponId);


}
