package com.haocp.tilab.entity;

import com.haocp.tilab.enums.TokenType;
import com.haocp.tilab.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false, unique = true)
    String token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TokenType type;

    @Column(nullable = false)
    Instant expiredAt;

    @Column(nullable = false)
    boolean used = false;

    @Column(nullable = false, updatable = false)
    Instant createdAt = Instant.now();

}
