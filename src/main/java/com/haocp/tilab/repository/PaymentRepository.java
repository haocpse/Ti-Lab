package com.haocp.tilab.repository;

import com.haocp.tilab.dto.response.Dashboard.PaymentDashboardResponse;
import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.enums.PaymentStatus;
import com.haocp.tilab.repository.Projection.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findPaymentByOrder_Id(String orderId);
    Optional<Payment> findPaymentByOrder_IdAndMethod(String orderId, PayMethod method);

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

    @Query(value = """
            WITH RECURSIVE date_series AS (
                SELECT DATE(:fromDate) AS period
                UNION ALL
                SELECT DATE_ADD(period, INTERVAL 1 DAY)
                FROM date_series
                WHERE period < DATE(:toDate)
            )
            SELECT ds.period,
                   COALESCE(SUM(p.total), 0) AS amount,
                   COALESCE(COUNT(p.id), 0) AS total_payments
            FROM date_series ds
            LEFT JOIN payment p
                   ON DATE(p.created_at) = ds.period
                  AND p.status = 'PAID'
                  AND p.pay_at IS NOT NULL
            GROUP BY ds.period
            ORDER BY ds.period
            """, nativeQuery = true)
    List<PaymentStatSummary> getPaymentStatsByDay(@Param("fromDate") LocalDateTime fromDate,
                                                  @Param("toDate") LocalDateTime toDate);

    @Query(value = """
            WITH RECURSIVE week_series AS (
                SELECT YEARWEEK(:fromDate, 1) AS period
                UNION ALL
                SELECT period + 1
                FROM week_series
                WHERE period < YEARWEEK(:toDate, 1)
            )
            SELECT ws.period,
                   COALESCE(SUM(p.total), 0) AS amount,
                   COALESCE(COUNT(p.id), 0) AS total_payments
            FROM week_series ws
            LEFT JOIN payment p
                   ON YEARWEEK(p.created_at, 1) = ws.period
                  AND p.status = 'PAID'
                  AND p.pay_at IS NOT NULL
            GROUP BY ws.period
            ORDER BY ws.period
            """, nativeQuery = true)
    List<PaymentStatSummary> getPaymentStatsByWeek(@Param("fromDate") LocalDateTime fromDate,
                                                   @Param("toDate") LocalDateTime toDate);

    @Query(value = """
            WITH RECURSIVE month_series AS (
                SELECT DATE_FORMAT(:fromDate, '%Y-%m') AS period
                UNION ALL
                SELECT DATE_FORMAT(DATE_ADD(CONCAT(period, '-01'), INTERVAL 1 MONTH), '%Y-%m')
                FROM month_series
                WHERE CONCAT(period, '-01') < DATE(:toDate)
            )
            SELECT ms.period,
                   COALESCE(SUM(p.total), 0) AS amount,
                   COALESCE(COUNT(p.id), 0) AS total_payments
            FROM month_series ms
            LEFT JOIN payment p
                   ON DATE_FORMAT(p.created_at, '%Y-%m') = ms.period
                  AND p.status = 'PAID'
                  AND p.pay_at IS NOT NULL
            GROUP BY ms.period
            ORDER BY ms.period
            """, nativeQuery = true)
    List<PaymentStatSummary> getPaymentStatsByMonth(@Param("fromDate") LocalDateTime fromDate,
                                                    @Param("toDate") LocalDateTime toDate);


    @Query(value = """
            WITH methods AS (
                SELECT 'CARD' AS method
                UNION ALL
                SELECT 'COD'
            )
            SELECT 
                m.method AS method,
                COALESCE(COUNT(p.id), 0) AS total,
                COALESCE(
                    ROUND(
                        COUNT(p.id) * 100.0 /
                        NULLIF((
                            SELECT COUNT(*) 
                            FROM payment p2
                            WHERE p2.status <> 'FAILED'
                              AND p2.created_at BETWEEN :fromDate AND :toDate
                        ), 0),
                        2
                    ), 0
                ) AS percentage
            FROM methods m
            LEFT JOIN payment p
                   ON p.method = m.method
                  AND p.status <> 'FAILED'
                  AND p.created_at BETWEEN :fromDate AND :toDate
            GROUP BY m.method
            ORDER BY total DESC
            """, nativeQuery = true)
    List<PaymentMethodSummary> getPaymentMethodStats(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);


    @Query(value = """
            SELECT
                COUNT(*) AS total,
                COALESCE(SUM(p.total), 0) AS totalPrice,
                ROUND(COALESCE(AVG(p.total), 0), 2) AS avgPrice
            FROM payment p
            WHERE p.status = 'PAID'
              AND p.pay_at IS NOT NULL
              AND p.created_at BETWEEN :fromDate AND :toDate
            """, nativeQuery = true)
    PaymentStatOverview getPaymentOverview(@Param("fromDate") LocalDateTime fromDate,
                                           @Param("toDate") LocalDateTime toDate);


    @Query(value = """
            SELECT 
                p.total
            FROM payment p
            WHERE p.pay_at IS NOT NULL
              AND p.status = 'PAID'
              AND p.created_at BETWEEN :fromDate AND :toDate
            ORDER BY p.total DESC
            LIMIT 3
            """, nativeQuery = true)
    List<MostTotalPricePayment> findTop3PaymentsByTotal(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);

    Page<Payment> findPaymentByStatusOrderByPayAtDesc(PaymentStatus status, Pageable pageable);

}
