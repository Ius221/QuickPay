package com.example.project.first.QuickPay.service;

import com.example.project.first.QuickPay.dto.TransactionResponseDto;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.repository.TransactionRepository;
import com.example.project.first.QuickPay.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
                        Objects.equals(transaction.getUser().getUsername(), transaction.getUsername()) ? "Self":
                                transaction.getUser().getUsername(),
                        transaction.getWallet().getAccNo().toString(),
                        transaction.getTransactionTime()
                        ))
                .toList();

        return allResponseDtos;
    }
}
