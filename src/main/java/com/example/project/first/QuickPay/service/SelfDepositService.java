package com.example.project.first.QuickPay.service;

import com.example.project.first.QuickPay.dto.SelfRequestDto;
import com.example.project.first.QuickPay.dto.SelfResponseDto;
import com.example.project.first.QuickPay.entity.Status;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.entity.Wallet;
import com.example.project.first.QuickPay.repository.TransactionRepository;
import com.example.project.first.QuickPay.repository.UserRepository;
import com.example.project.first.QuickPay.repository.WalletRepository;
import jakarta.validation.Valid;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SelfDepositService {

    @Autowired private WalletRepository walletRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public SelfResponseDto depositMoney(SelfRequestDto depositRequestDto) {

        User currUser = userRepository.findByEmail(depositRequestDto.getEmail()).orElse(null);
        if(currUser == null) throw  new IllegalArgumentException("User not found with email: "+depositRequestDto.getEmail());

        Wallet currWallet = currUser.getWallet();

        currWallet.setMoney(currWallet.getMoney() + depositRequestDto.getMoney());

        Wallet updatedWallet = walletRepository.save(currWallet);

        Transaction transaction = new Transaction();
        transaction.setMoney(depositRequestDto.getMoney());
        transaction.setMoneyStatus(Status.DEPOSIT);
        transaction.setSenderName("Self");
        transaction.setReceiverName("Self");
        transaction.setSenderAccNo(currWallet.getAccNo());
        transaction.setReceiverAccNo(currWallet.getAccNo());
        transaction.setUser( currUser);

        Transaction savedTransaction = transactionRepository.save(transaction);

        SelfResponseDto response = new  SelfResponseDto(currUser.getUsername(), updatedWallet.getMoney(),
                updatedWallet.getAccNo(), savedTransaction.getTransactionTime());

      return response;
    }

    public SelfResponseDto withdrawFund(@Valid SelfRequestDto selfRequestDto) {
        User currUser = userRepository.findByEmail(selfRequestDto.getEmail()).orElse(null);
        if(currUser == null) throw  new IllegalArgumentException("User not found with email: "+selfRequestDto.getEmail());

        Wallet currWallet = currUser.getWallet();

        if(currUser.getWallet().getMoney() < selfRequestDto.getMoney()) throw new IllegalArgumentException(
                "Insufficient Balance");

        currWallet.setMoney(currWallet.getMoney() - selfRequestDto.getMoney());

        Wallet updatedWallet = walletRepository.save(currWallet);

        Transaction transaction = new Transaction();
        transaction.setMoney(selfRequestDto.getMoney());
        transaction.setMoneyStatus(Status.WITHDRAW);
        transaction.setSenderName("Self");
        transaction.setReceiverName("Self");
        transaction.setSenderAccNo(currWallet.getAccNo());
        transaction.setReceiverAccNo(currWallet.getAccNo());
        transaction.setUser( currUser);

        Transaction savedTransaction = transactionRepository.save(transaction);

        SelfResponseDto response = new  SelfResponseDto(currUser.getUsername(), updatedWallet.getMoney(),
                updatedWallet.getAccNo(), savedTransaction.getTransactionTime());

        return response;
    }

}
