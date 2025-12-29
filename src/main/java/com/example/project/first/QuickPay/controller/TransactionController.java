package com.example.project.first.QuickPay.controller;

import com.example.project.first.QuickPay.dto.MoneyTransferRequestDto;
import com.example.project.first.QuickPay.dto.MoneyTransferResponseDto;
import com.example.project.first.QuickPay.dto.SelfRequestDto;
import com.example.project.first.QuickPay.dto.SelfResponseDto;
import com.example.project.first.QuickPay.service.MoneyTransferService;
import com.example.project.first.QuickPay.service.SelfDepositService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransactionController {

    @Autowired
    private SelfDepositService selfDeposit;
    @Autowired
    private MoneyTransferService moneyTransferService;

    @PostMapping("/self/deposit")
    public ResponseEntity<SelfResponseDto> depositMoney(
            @RequestParam String username,
            @Valid @RequestBody SelfRequestDto depositRequestDto
    ){
        SelfResponseDto depositResponseDto = selfDeposit.depositMoney(depositRequestDto, username);
        return new ResponseEntity<>(depositResponseDto, HttpStatus.OK);
    }

    @PostMapping("/self/withdraw")
    public ResponseEntity<SelfResponseDto> withdrawMoney(
            @RequestParam String username,
            @Valid @RequestBody SelfRequestDto selfRequestDto
    ){
        SelfResponseDto selfResponseDto = selfDeposit.withdrawFund(selfRequestDto, username);

        return  new ResponseEntity<>(selfResponseDto, HttpStatus.OK);
    }

    @PostMapping("/other")
    public ResponseEntity<MoneyTransferResponseDto> transferMoney(
            @RequestParam String username,
            @RequestBody MoneyTransferRequestDto moneyTransferRequestDto
            ){
        MoneyTransferResponseDto moneyTransferResponseDto =  moneyTransferService.transferMoney(username,
                moneyTransferRequestDto);
        return new ResponseEntity<>(moneyTransferResponseDto, HttpStatus.OK);
    }

}
