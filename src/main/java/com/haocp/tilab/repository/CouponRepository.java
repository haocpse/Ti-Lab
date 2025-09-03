package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Coupon;
import com.haocp.tilab.entity.CouponUsage;
import com.haocp.tilab.enums.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findCouponByCouponsNotInAndStatus(Collection<Set<CouponUsage>> coupons, CouponStatus status);

}
