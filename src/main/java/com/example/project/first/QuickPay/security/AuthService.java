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

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
//        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        User user = userRepository.findByEmail(signupRequestDto.getEmail()).orElse(null);

        if(user != null ) throw new IllegalArgumentException(" Email Already Used");

        validatePassword(signupRequestDto.getPassword());

        Wallet wallet = new Wallet();
        wallet.setMoney(500D);

      user = userRepository.save(
              User.builder()
                      .username(signupRequestDto.getUsername())
                      .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                      .email(signupRequestDto.getEmail())
                      .wallet(wallet)
                      .build()
      );

    return new SignupResponseDto(user.getUsername(),wallet.getAccNo(), wallet.getMoney());
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        System.out.println(loginRequestDto);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        User user = (User)authentication.getPrincipal();

        assert user != null;
        String token = jwtUtil.generateAccessToken(user);

        return new LoginResponseDto( token, user.getUsername(),user.getWallet().getAccNo(), user.getWallet().getMoney());

    }

    public void validatePassword(String password) {

        if (password == null)
            throw new IllegalArgumentException("Password cannot be null");

        if (password.length() < 5)
            throw new IllegalArgumentException("Password must be at least 5 characters long");

        if (!password.matches(".*[A-Za-z].*"))
            throw new IllegalArgumentException("Password must contain at least one alphabet");

        if (!password.matches(".*\\d.*"))
            throw new IllegalArgumentException("Password must contain at least one number");

        if (!password.matches(".*[^A-Za-z0-9].*"))
            throw new IllegalArgumentException("Password must contain at least one symbol");
    }

}
