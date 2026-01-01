package com.example.project.first.QuickPay.dto;

import com.example.project.first.QuickPay.config.Password;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequestDto {

    @NotNull
    private String username;

    @Password
    @NotNull
    private String password;
}
