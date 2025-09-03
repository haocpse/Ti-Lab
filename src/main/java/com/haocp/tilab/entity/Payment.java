package com.haocp.tilab.entity;

import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    Order order;

    @Column(nullable = false)
    double total;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PayMethod method;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    Instant updatedAt;

}
