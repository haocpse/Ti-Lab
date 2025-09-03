package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Order;
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
    List<Order> findAllWithDetails();

    @EntityGraph(attributePaths = {"customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    List<Order> findAllByCustomer_IdWithDetails(String customerId);

    @EntityGraph(attributePaths = {"coupon", "customer.user", "details.bag"})
    @Query("SELECT o FROM Order o")
    Optional<Order> findOrderById(String id);

}
