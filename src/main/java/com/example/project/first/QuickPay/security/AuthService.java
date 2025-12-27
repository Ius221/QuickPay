package com.example.project.first.QuickPay.security;

import com.example.project.first.QuickPay.dto.LoginRequestDto;
import com.example.project.first.QuickPay.dto.LoginResponseDto;
import com.example.project.first.QuickPay.dto.SignupRequestDto;
import com.example.project.first.QuickPay.dto.SignupResponseDto;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.entity.Wallet;
import com.example.project.first.QuickPay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    static Long accNo = 1000L;

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user != null) throw new IllegalArgumentException("Username Repeated");

        Wallet wallet = new Wallet();
        wallet.setMoney(500D);
        wallet.setAccNo(accNo++);

      user = userRepository.save(
              User.builder()
                      .username(signupRequestDto.getUsername())
                      .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                      .wallet(wallet)
                      .build()
      );

    return new SignupResponseDto(user.getUsername(),wallet.getAccNo(), wallet.getMoney());
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        User user = (User)authentication.getPrincipal();

        String token = jwtUtil.generateAccessToken(user);

//        System.out.println(user);

        return new LoginResponseDto(user.getUsername(), token);

    }
}
