package com.haocp.tilab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "pre_order")
public class PreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 50)
    String codeReport;

    @OneToMany(mappedBy = "preOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    Set<PreOrderDetail> details = new HashSet<>();

}
