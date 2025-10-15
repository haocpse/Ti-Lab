package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.repository.Projection.OrderSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Page<Order> findAllWithDetails(Pageable pageable);

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    Page<Order> findAllByCustomer_IdWithDetails(@Param("customerId") String customerId, Pageable pageable);

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Page<Order> findAllByCustomer_IdAndStatusWithDetails(String customerId, OrderStatus status, Pageable pageable);

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

}
