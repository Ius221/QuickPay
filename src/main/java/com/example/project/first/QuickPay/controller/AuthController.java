package com.example.project.first.QuickPay.controller;

import com.example.project.first.QuickPay.dto.LoginRequestDto;
import com.example.project.first.QuickPay.dto.LoginResponseDto;
import com.example.project.first.QuickPay.dto.SignupRequestDto;
import com.example.project.first.QuickPay.dto.SignupResponseDto;
import com.example.project.first.QuickPay.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        SignupResponseDto signupResponseDto = authService.signup(signupRequestDto);

        return  new ResponseEntity<>(signupResponseDto, HttpStatus.OK);
    }

}
