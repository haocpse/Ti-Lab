package com.haocp.tilab.repository;

import com.haocp.tilab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameAndActiveIsTrue(String username);

    Optional<User> findByEmailAndActiveIsTrue(String email);
}
