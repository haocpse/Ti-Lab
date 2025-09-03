package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.entity.Coupon;
import com.haocp.tilab.entity.CouponUsage;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.enums.CouponStatus;
import com.haocp.tilab.mapper.CouponMapper;
import com.haocp.tilab.repository.CouponRepository;
import com.haocp.tilab.repository.CouponUsageRepository;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.CouponService;
import com.haocp.tilab.utils.IdentifyUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CouponUsageRepository couponUsageRepository;

    @Override
    @Transactional
    public CouponResponse createCoupon(CreateCouponRequest request) {
        Coupon coupon = couponMapper.toCoupon(request);
        coupon.setStatus(CouponStatus.UNAVAILABLE);
        couponRepository.save(coupon);
        return couponMapper.toResponse(coupon);
    }

    @Override
    public CouponResponse updateCoupon(Coupon coupon) {
        return null;
    }

    @Override
    public void deleteCoupon(Coupon coupon) {

    }

    @Override
    public List<CouponResponse> getAllCoupon() {
        List<Coupon> coupons = couponRepository.findAll();
        List<CouponResponse> couponResponses = new ArrayList<>();
        for (Coupon coupon : coupons) {
            CouponResponse response = buildCouponResponse(coupon);
            couponResponses.add(response);
        }
        return couponResponses;
    }

    @Override
    public List<CouponResponse> getAllAvailableCoupon() {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        Set<CouponUsage> usageSet = couponUsageRepository.findByCustomer_Id(customer.getId());
        List<Coupon> coupons = couponRepository.findCouponByCouponsNotInAndStatus(Collections.singleton(usageSet), CouponStatus.AVAILABLE);
        List<CouponResponse> couponResponses = new ArrayList<>();
        for (Coupon coupon : coupons) {
            CouponResponse response = buildCouponResponse(coupon);
            couponResponses.add(response);
        }
        return couponResponses;
    }

    CouponResponse buildCouponResponse(Coupon coupon) {
        return couponMapper.toResponse(coupon);
    }
}
