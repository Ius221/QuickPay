package com.example.project.first.QuickPay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelfDepositResponseDto {

    private String username;
    private double money;
    private Long accNo;
    private LocalDateTime localDateTime;

}
