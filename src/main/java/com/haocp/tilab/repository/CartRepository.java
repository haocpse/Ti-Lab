package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
}
