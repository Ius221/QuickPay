package com.example.project.first.QuickPay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDto {
    private String token;
    private String username;
//    private Long accNo;
//    private Double money;
}
