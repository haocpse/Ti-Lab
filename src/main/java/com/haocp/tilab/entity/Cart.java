package com.haocp.tilab.entity;

import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.enums.PayMethod;
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
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bag_id")
    @ToString.Exclude
    Bag bag;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    Customer customer;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    Instant updatedAt;

}
