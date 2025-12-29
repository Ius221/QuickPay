package com.example.project.first.QuickPay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelfRequestDto {

    @NotNull
    private Long accNo;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive value")
    private double money;

    private String password;
}
