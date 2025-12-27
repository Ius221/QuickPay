package com.example.project.first.QuickPay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupResponseDto {
    private String username;
    private Long accNo;
    private Double money;
}
