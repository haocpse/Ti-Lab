package com.haocp.tilab.entity;

import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
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
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bag")
public class Bag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, length = 200)
    String name;

    @Column(nullable = false)
    double price;

    @Column(nullable = false)
    String description;

    @Column(nullable = false, length = 30)
    String author;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BagStatus status;

    @Column(nullable = false)
    double length;

    @Column(nullable = false)
    double weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BagType type;

    @OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    Set<BagImg> images = new HashSet<>();

    @OneToMany(mappedBy = "bag")
    @ToString.Exclude
    Set<OrderDetail> orderDetails = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    Instant updatedAt;

}
