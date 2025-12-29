package com.example.project.first.QuickPay.controller;

import com.example.project.first.QuickPay.dto.TransactionResponseDto;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/show")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/transaction")
    public ResponseEntity<List<TransactionResponseDto>> showTransaction(@RequestParam String username){

        List<TransactionResponseDto> responseDto = recordService.showTransaction(username);

        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);

    }

}
