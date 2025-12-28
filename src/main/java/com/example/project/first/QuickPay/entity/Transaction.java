package com.example.project.first.QuickPay.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive value")
    private Double money;

    @Enumerated(EnumType.STRING)
    private Status moneyStatus;

    @NotNull
    private String username;

    @NotNull
    private Long accNo;

    @ManyToOne
    private User user;

    @ManyToOne
    private Wallet wallet;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime transactionTime;
}
