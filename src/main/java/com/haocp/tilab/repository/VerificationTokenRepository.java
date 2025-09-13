package com.haocp.tilab.repository;

import com.haocp.tilab.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByTokenAndUser_IdAndUsed(String token,String userId, boolean used);
    Optional<VerificationToken> findByTokenAndUsed(String token, boolean used);
    Optional<VerificationToken> findByToken(String token);

}
