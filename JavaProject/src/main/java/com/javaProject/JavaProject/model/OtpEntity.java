package com.javaProject.JavaProject.model;



import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "otp_entities")
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String otp;

    @Column(name = "retry_count", nullable = false)
    private int retryCount = 0;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked = false;
}
