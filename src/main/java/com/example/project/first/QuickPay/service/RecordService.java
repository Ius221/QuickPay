package com.example.project.first.QuickPay.service;

import com.example.project.first.QuickPay.dto.TransactionResponse;
import com.example.project.first.QuickPay.dto.TransactionResponseDto;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.entity.Wallet;
import com.example.project.first.QuickPay.repository.TransactionRepository;
import com.example.project.first.QuickPay.repository.UserRepository;
import com.example.project.first.QuickPay.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private ModelMapper modelMapper;


    public TransactionResponse showTransaction(
            Long accNo,
            Integer pageNumber,
            String sortPage,
            Integer pageSize,
            String sortOrder) {

        Wallet currWallet = walletRepository.findById(accNo).orElseThrow();

        Sort sortByAndOrder =
                sortOrder.equalsIgnoreCase("asc")?
                        Sort.by(sortPage).ascending() :
                        Sort.by(sortPage).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Transaction> transactionPage = transactionRepository.findByUser(currWallet.getUser(),pageDetails);

        List<Transaction> allTransaction = transactionPage.getContent();
        if(allTransaction.isEmpty()) throw  new IllegalArgumentException("No Transaction Found");

        List<TransactionResponseDto> transactionResponseDtos = allTransaction
                .stream()
                .map( transaction -> new TransactionResponseDto(
                        transaction.getMoneyStatus().toString(),
                        transaction.getMoney(),
                                transaction.getSenderName(),
                        transaction.getSenderAccNo().toString(),
                        transaction.getTransactionTime()
                        ))
                .toList();

        TransactionResponse response = new TransactionResponse();
        response.setContent(transactionResponseDtos);
        response.setTotalPage(transactionPage.getTotalPages());
        response.setTotalElement(transactionPage.getTotalElements());
        response.setPageSize(transactionPage.getSize());
        response.setPageNumber(transactionPage.getNumber());
        response.setIsLast(transactionPage.isLast());
        response.setIsFirst(transactionPage.isFirst());
        return response;
    }

    public double getCurrentBalance(String username) {

        User currUser = userRepository.findByUsername(username).orElseThrow();

        return currUser.getWallet().getMoney();

    }
}
