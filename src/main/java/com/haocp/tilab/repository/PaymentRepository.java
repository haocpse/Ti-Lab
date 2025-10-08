package com.haocp.tilab.repository;

import com.haocp.tilab.dto.response.Dashboard.PaymentDashboardResponse;
import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.enums.PaymentStatus;
import com.haocp.tilab.repository.Projection.PaymentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findPaymentByOrder_Id(String orderId);

    @Query("SELECT FUNCTION('YEAR', p.payAt) as year, " +
            "FUNCTION('MONTH', p.payAt) as month, " +
            "p.method as method, " +
            "SUM(p.total) as total " +
            "FROM Payment p " +
            "WHERE p.payAt BETWEEN :from AND :to " +
            "GROUP BY FUNCTION('YEAR', p.payAt), FUNCTION('MONTH', p.payAt), p.method")
    List<PaymentSummary> getPaymentSummary(@Param("from") LocalDate from,
                                           @Param("to") LocalDate to);

    List<Payment> findPaymentByStatus(PaymentStatus status);

}
