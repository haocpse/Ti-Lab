package com.haocp.tilab.entity;

import com.haocp.tilab.enums.BagType;
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
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    int numberOfBag;

    @Column(nullable = false)
    double subTotal;

    @Column(nullable = false)
    int feeOfDelivery;

    @Column(nullable = false)
    double total;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PayMethod method;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coupon_id")
    @ToString.Exclude
    Coupon coupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    Set<OrderDetail> details = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    Instant updatedAt;

}
