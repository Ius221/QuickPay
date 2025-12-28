package com.example.project.first.QuickPay.controller;

import com.example.project.first.QuickPay.dto.SelfDepositRequestDto;
import com.example.project.first.QuickPay.dto.SelfResponseDto;
import com.example.project.first.QuickPay.service.SelfDeposit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer/self")
public class SelfTransactionController {

    @Autowired
    private SelfDeposit selfDeposit;

    @PostMapping("/deposit")
    public ResponseEntity<SelfResponseDto> depositMoney(
            @RequestParam String username,
            @Valid @RequestBody SelfDepositRequestDto depositRequestDto
    ){

        SelfResponseDto depositResponseDto = selfDeposit.depositMoney(depositRequestDto, username);

        return new ResponseEntity<>(depositResponseDto, HttpStatus.OK);

    }

    @PostMapping("/withdraw")
    public ResponseEntity<SelfResponseDto> withdrawMoney(
            @RequestParam String username,
            @Valid @RequestBody SelfDepositRequestDto selfDepositRequestDto
    ){
        SelfResponseDto selfResponseDto = selfDeposit.withdrawFund(selfDepositRequestDto, username);

        return  new ResponseEntity<>(selfResponseDto, HttpStatus.OK);
    }
}
