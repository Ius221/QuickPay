package com.example.project.first.QuickPay.security;

import com.example.project.first.QuickPay.dto.SignupRequestDto;
import com.example.project.first.QuickPay.dto.SignupResponseDto;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        System.out.println(user+" + "+ signupRequestDto);

        if(user != null) throw new IllegalArgumentException("Username Repeated");

      user = userRepository.save(
              User.builder()
                      .username(signupRequestDto.getUsername())
                      .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                      .build()
      );
        System.out.println(user+"<-----");
    return new SignupResponseDto(user.getUsername(), user.getPassword());

    }
}
