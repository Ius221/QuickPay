package com.example.project.first.QuickPay.controller;

import com.example.project.first.QuickPay.dto.SelfRequestDto;
import com.example.project.first.QuickPay.dto.SelfResponseDto;
import com.example.project.first.QuickPay.service.SelfDepositService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer/self")
public class SelfTransactionController {

    @Autowired
    private SelfDepositService selfDeposit;

    @PostMapping("/deposit")
    public ResponseEntity<SelfResponseDto> depositMoney(
            @RequestParam String username,
            @Valid @RequestBody SelfRequestDto depositRequestDto
    ){

        SelfResponseDto depositResponseDto = selfDeposit.depositMoney(depositRequestDto, username);

        return new ResponseEntity<>(depositResponseDto, HttpStatus.OK);

    }

    @PostMapping("/withdraw")
    public ResponseEntity<SelfResponseDto> withdrawMoney(
            @RequestParam String username,
            @Valid @RequestBody SelfRequestDto selfRequestDto
    ){
        SelfResponseDto selfResponseDto = selfDeposit.withdrawFund(selfRequestDto, username);

        return  new ResponseEntity<>(selfResponseDto, HttpStatus.OK);
    }
}
