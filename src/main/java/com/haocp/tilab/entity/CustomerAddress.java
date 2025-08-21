package com.haocp.tilab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customer_address")
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100)
    String address;

    @Column(length = 30)
    String city;

    @Column(nullable = false)
    boolean isDefaultShipping = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    Customer customer;

}
