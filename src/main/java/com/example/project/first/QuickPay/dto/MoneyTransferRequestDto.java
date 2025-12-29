package com.example.project.first.QuickPay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MoneyTransferRequestDto {
    private String password;
    private double money;
    private long accNo;

}
