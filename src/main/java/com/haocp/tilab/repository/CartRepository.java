package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByBag_IdAndCustomer_Id(String cartId, String customerId);

    @EntityGraph(attributePaths = {"customer.user", "bag"})
    @Query("SELECT o FROM Cart o")
    Page<Cart> findAllWithDetails(Pageable pageable);

    @EntityGraph(attributePaths = {"customer.user", "bag"})
    @Query("SELECT o FROM Cart o")
    Page<Cart> findAllByCustomer_IdWithDetails(String customerId, Pageable pageable);

}
