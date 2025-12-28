package com.example.project.first.QuickPay.service;

import com.example.project.first.QuickPay.dto.SelfDepositRequestDto;
import com.example.project.first.QuickPay.dto.SelfDepositResponseDto;
import com.example.project.first.QuickPay.entity.Status;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.entity.Wallet;
import com.example.project.first.QuickPay.repository.TransactionRepository;
import com.example.project.first.QuickPay.repository.UserRepository;
import com.example.project.first.QuickPay.repository.WalletRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SelfDeposit {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public SelfDepositResponseDto depositMoney(SelfDepositRequestDto depositRequestDto,String username) {

        User currUser = userRepository.findByUsername(username).orElse(null);
        Wallet wallet = walletRepository.findById(depositRequestDto.getAccNo()).orElse(null);

        if(wallet == null || currUser == null) throw new IllegalArgumentException("Username not Match with account " +
                "Number");

        Transaction transaction = new Transaction();
        transaction.setMoney(depositRequestDto.getMoney());
        transaction.setMoneyStatus(Status.DEPOSIT);
        transaction.setUsername(currUser.getUsername());
        transaction.setAccNo(wallet.getAccNo());
        transaction.setWallet(wallet);
        transaction.setUser(currUser);

        transactionRepository.save(transaction);

        wallet.setMoney( wallet.getMoney() + depositRequestDto.getMoney());

        wallet = walletRepository.save(wallet);

        return new SelfDepositResponseDto(wallet.getUser().getUsername(), wallet.getMoney(), wallet.getAccNo(),LocalDateTime.now());

    }
}
