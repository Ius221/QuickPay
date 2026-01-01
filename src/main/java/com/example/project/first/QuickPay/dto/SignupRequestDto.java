package com.example.project.first.QuickPay.dto;

import com.example.project.first.QuickPay.config.Password;
import jakarta.validation.constraints.Email;
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

    @Email
    private String email;

    @Password
    @NotNull
    private String password;
}
