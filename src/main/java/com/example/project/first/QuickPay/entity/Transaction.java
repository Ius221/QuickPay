package com.example.project.first.QuickPay.entity;

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
    @Column(name = "amount")
    private Double money;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status moneyStatus;

    @NotNull
    @Column(name = "From")
    private String senderName;

    @NotNull
    @Column(name = "To")
    private String receiverName;

    @NotNull
    private Long receiverAccNo;

    @NotNull
    private Long senderAccNo;

    @ManyToOne
    @JoinColumn(name = "User")
    @JsonManagedReference
    @ToString.Exclude
    private User user;

    @CreationTimestamp
    @Column(updatable = false,name = "Time")
    private LocalDateTime transactionTime;
}
