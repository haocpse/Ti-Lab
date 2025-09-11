package com.haocp.tilab.entity;

import com.haocp.tilab.enums.EmailStatus;
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
@Table(name = "email_queue")
public class EmailQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    EmailTemplate template;

    @Column(nullable = false)
    String recipient;

    @Column(nullable = false)
    String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    String body;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    EmailStatus status;

    @Column(nullable = false)
    int retryCount = 0;

    @Column(columnDefinition = "TEXT")
    String errorMessage;

    Instant sentAt;

}
