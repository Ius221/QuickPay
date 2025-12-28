package com.example.project.first.QuickPay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Wallet {

    public Double  money;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_no_generator")
    @SequenceGenerator(
            name = "acc_no_generator",
            sequenceName = "wallet_acc_no_seq",
            initialValue = 1001, // Starts at 1001
            allocationSize = 1   // Increases by 1 each time
    )
    private Long accNo;

    @OneToOne(mappedBy = "wallet")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transaction;
}
