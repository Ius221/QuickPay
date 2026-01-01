package com.example.project.first.QuickPay.dto;

import com.example.project.first.QuickPay.config.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDto {
    @NotNull
    private String email;

    @NotBlank
    @Password(message = "Password is weak")
    private String password;
}
