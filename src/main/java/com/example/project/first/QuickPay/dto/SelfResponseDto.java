package com.example.project.first.QuickPay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelfResponseDto {

    private String username;
    private double money;
    private Long accNo;
    private LocalDateTime localDateTime;

}
