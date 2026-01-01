package com.example.project.first.QuickPay.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";


    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s == null) return false;
        return s.matches(PASSWORD_PATTERN);
    }
}
