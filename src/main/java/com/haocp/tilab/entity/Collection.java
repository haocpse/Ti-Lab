package com.haocp.tilab.entity;

import com.haocp.tilab.enums.CollectionStatus;
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
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 100)
    String name;

    @Column
    String thumbnail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    CollectionStatus status;

    @OneToMany(mappedBy = "collection")
    @ToString.Exclude
    Set<Bag> bags = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    Instant updatedAt;

}
