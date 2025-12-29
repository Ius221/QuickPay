package com.example.project.first.QuickPay.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {
    private String status;
    private Double money;
    private String senderName;
    private String senderAccNo;
    private LocalDateTime time;
}
