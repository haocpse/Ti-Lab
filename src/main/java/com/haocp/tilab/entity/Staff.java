package com.haocp.tilab.entity;

import com.haocp.tilab.enums.StaffRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "staff")
public class Staff {

    @Id
    String id;

    @Column(name = "staff_code", nullable = false, length = 50)
    String staffCode;
    @Column(nullable = false, length = 100)
    String firstName;
    @Column(nullable = false, length = 100)
    String lastName;
    @Column(name = "business_email", length = 100)
    String businessEmail;
    @Column(nullable = false, name = "role", length = 10)
    @Enumerated(EnumType.STRING)
    StaffRole role;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    @ToString.Exclude
    User user;

}
