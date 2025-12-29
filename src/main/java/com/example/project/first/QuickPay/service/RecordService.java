package com.example.project.first.QuickPay.service;

import com.example.project.first.QuickPay.dto.PasswordRequestDto;
import com.example.project.first.QuickPay.dto.TransactionResponseDto;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.repository.TransactionRepository;
import com.example.project.first.QuickPay.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RecordService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<TransactionResponseDto> showTransaction(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();

        List<Transaction> allTransactions = transactionRepository.findByUserId(user.getId());

        if (allTransactions.isEmpty())
            throw new IllegalArgumentException("No transactions found for user: " + username);

        List<TransactionResponseDto> allResponseDtos = allTransactions
                .stream()
                .map( transaction -> new TransactionResponseDto(
                        transaction.getMoneyStatus().toString(),
                        transaction.getMoney(),
//                        Objects.equals(transaction.getUser().getUsername(), transaction.getUsername()) ? "Self":
                                transaction.getUsername(),
                        transaction.getAccNo().toString(),
                        transaction.getTransactionTime()
                        ))
                .toList();

        return allResponseDtos;
    }

    public double getCurrentBalance(String username, PasswordRequestDto passwordRequestDto) {

        User currUser = userRepository.findByUsername(username).orElseThrow();

        if(!passwordEncoder.matches(passwordRequestDto.getPassword(),currUser.getPassword()))
            throw new IllegalArgumentException("Password doesn't Match");

        return currUser.getWallet().getMoney();

    }
}
