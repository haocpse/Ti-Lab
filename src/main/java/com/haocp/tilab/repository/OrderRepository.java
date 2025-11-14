package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.repository.Projection.NumberOrderSummary;
import com.haocp.tilab.repository.Projection.OrderStatusSummary;
import com.haocp.tilab.repository.Projection.OrderSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o ORDER BY o.createdAt desc ")
    Page<Order> findAllWithDetails(Pageable pageable);

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    Page<Order> findAllByCustomer_IdWithDetails(@Param("customerId") String customerId, Pageable pageable);

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Page<Order> findAllByCustomer_IdAndStatusWithDetails(String customerId, OrderStatus status, Pageable pageable);

    List<Order> findAllByCustomer_Id(String customerId);

    @EntityGraph(attributePaths = {"coupon", "customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Optional<Order> findOrderById(String id);

    int countOrderByStatusIn(List<OrderStatus> statuses);

    @Query("SELECT FUNCTION('YEAR', o.createdAt) as year, " +
            "FUNCTION('MONTH', o.createdAt) as month, " +
            "o.status as status, " +
            "COUNT(o) as total " +
            "FROM Order o " +
            "WHERE o.createdAt BETWEEN :from AND :to " +
            "GROUP BY FUNCTION('YEAR', o.createdAt), FUNCTION('MONTH', o.createdAt), o.status ")
    List<OrderSummary> getOrderSummary(@Param("from") LocalDate from,
                                       @Param("to") LocalDate to);


    @Query(value = """
    WITH RECURSIVE date_series AS (
        SELECT DATE(:fromDate) AS period
        UNION ALL
        SELECT DATE_ADD(period, INTERVAL 1 DAY)
        FROM date_series
        WHERE period < DATE(:toDate)
    )
    SELECT 
        ds.period AS period,
        COALESCE(COUNT(o.id), 0) AS total_orders
    FROM date_series ds
    LEFT JOIN orders o ON DATE(o.created_at) = ds.period
    GROUP BY ds.period
    ORDER BY ds.period
    """, nativeQuery = true)
    List<NumberOrderSummary> getOrderStatsByDay(@Param("fromDate") LocalDateTime fromDate,
                                                @Param("toDate") LocalDateTime toDate);


    @Query(value = """
    WITH RECURSIVE week_series AS (
        SELECT YEARWEEK(:fromDate, 1) AS week
        UNION ALL
        SELECT week + 1
        FROM week_series
        WHERE week < YEARWEEK(:toDate, 1)
    )
    SELECT 
        ws.week AS period,
        COALESCE(COUNT(o.id), 0) AS total_orders
    FROM week_series ws
    LEFT JOIN orders o ON YEARWEEK(o.created_at, 1) = ws.week
    GROUP BY ws.week
    ORDER BY ws.week
    """, nativeQuery = true)
    List<NumberOrderSummary> getOrderStatsByWeek(@Param("fromDate") LocalDateTime fromDate,
                                                 @Param("toDate") LocalDateTime toDate);


    @Query(value = """
    WITH RECURSIVE month_series AS (
        SELECT DATE_FORMAT(:fromDate, '%Y-%m-01') AS period
        UNION ALL
        SELECT DATE_ADD(period, INTERVAL 1 MONTH)
        FROM month_series
        WHERE period < DATE_FORMAT(:toDate, '%Y-%m-01')
    )
    SELECT 
        DATE_FORMAT(ms.period, '%Y-%m') AS period,
        COALESCE(COUNT(o.id), 0) AS total_orders
    FROM month_series ms
    LEFT JOIN orders o 
        ON DATE_FORMAT(o.created_at, '%Y-%m') = DATE_FORMAT(ms.period, '%Y-%m')
    GROUP BY period
    ORDER BY period
    """, nativeQuery = true)
    List<NumberOrderSummary> getOrderStatsByMonth(@Param("fromDate") LocalDateTime fromDate,
                                                  @Param("toDate") LocalDateTime toDate);


    @Query(value = """
    WITH status_group AS (
        SELECT 'PREPARING' AS grouped_status
        UNION ALL
        SELECT 'DELIVERING'
        UNION ALL
        SELECT 'COMPLETED'
        UNION ALL
        SELECT 'CANCELLED'
    )
    SELECT 
        sg.grouped_status AS groupedStatus,
        COALESCE(COUNT(o.id), 0) AS total
    FROM status_group sg
    LEFT JOIN orders o ON
        CASE
            WHEN o.status IN ('PREPARING') THEN 'PREPARING'
            WHEN o.status IN ('DELIVERING') THEN 'DELIVERING'
            WHEN o.status IN ('DELIVERED', 'COMPLETED') THEN 'COMPLETED'
            WHEN o.status IN ('CANCELLED', 'FAILED', 'REFUNDED', 'RETURNED') THEN 'CANCELLED'
            ELSE NULL
        END = sg.grouped_status
        AND o.created_at BETWEEN :fromDate AND :toDate
    GROUP BY sg.grouped_status
    ORDER BY sg.grouped_status
    """, nativeQuery = true)
    List<OrderStatusSummary> getOrderStatusSummary(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);

}
