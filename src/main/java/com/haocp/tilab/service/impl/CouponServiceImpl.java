package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.entity.Coupon;
import com.haocp.tilab.mapper.CouponMapper;
import com.haocp.tilab.repository.CouponRepository;
import com.haocp.tilab.service.CouponService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CouponMapper couponMapper;

    @Override
    @Transactional
    public CouponResponse createCoupon(CreateCouponRequest request) {
        Coupon coupon = couponRepository.save(couponMapper.toCoupon(request));
        return couponMapper.toResponse(coupon);
    }
}
