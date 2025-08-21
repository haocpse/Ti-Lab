package com.haocp.tilab.entity;

import com.haocp.tilab.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, unique = true, length = 100)
    String username;
    @Column(nullable = false, unique = true, length = 100)
    String email;
    @Column(nullable = false, length = 100)
    String password;

    @Column(length = 15)
    String phone;

    @Column(nullable = false, name = "role", length = 10)
    @Enumerated(EnumType.STRING)
    UserRole role;

    @Column(length = 200)
    String reasonBan;
    @Column(nullable = false)
    boolean active;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    Instant updatedAt;

}
