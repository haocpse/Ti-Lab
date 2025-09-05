package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Page<Order> findAllWithDetails(Pageable pageable);

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Page<Order> findAllByCustomer_IdWithDetails(String customerId, Pageable pageable);

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Page<Order> findAllByCustomer_IdAndStatusWithDetails(String customerId, OrderStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"coupon", "customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Optional<Order> findOrderById(String id);

}
