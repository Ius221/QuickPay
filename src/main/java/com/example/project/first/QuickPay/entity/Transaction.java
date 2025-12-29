package com.example.project.first.QuickPay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @JoinColumn(name = "User")
    @JsonManagedReference
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "Wallet")
    @JsonManagedReference
    @ToString.Exclude
    private Wallet wallet;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime transactionTime;
}
