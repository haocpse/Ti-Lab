package com.haocp.tilab.repository;

import com.haocp.tilab.entity.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {

    Set<CouponUsage> findByCustomer_Id(String customerId);

}
