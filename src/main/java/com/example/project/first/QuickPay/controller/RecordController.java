package com.example.project.first.QuickPay.controller;

import com.example.project.first.QuickPay.config.AppConstants;
import com.example.project.first.QuickPay.dto.PasswordRequestDto;
import com.example.project.first.QuickPay.dto.TransactionResponse;
import com.example.project.first.QuickPay.dto.TransactionResponseDto;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/show")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/transaction")
    public ResponseEntity<TransactionResponse> showTransaction(
            @RequestParam Long accNo,
            @RequestParam(
                    name = "pageNumber",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false
            ) Integer pageNumber,
            @RequestParam(
                    name = "sortBy",
                    defaultValue = AppConstants.SORT_TRANSACTIONS_BY,
                    required = false
            ) String sortPage,
            @RequestParam(
                    name = "pageSize",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false
            ) Integer pageSize,
            @RequestParam(
                    name = "sortOrder",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false
            ) String sortOrder
    ){
        TransactionResponse responseDto = recordService.showTransaction(accNo,pageNumber, sortPage, pageSize, sortOrder);

        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> showBalance(@RequestParam Long accNo){
        double currBalance = recordService.getCurrentBalance(accNo);
        return  new ResponseEntity<>(currBalance, HttpStatus.OK);
    }

}
