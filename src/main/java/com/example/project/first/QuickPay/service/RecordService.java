package com.example.project.first.QuickPay.service;

import com.example.project.first.QuickPay.dto.TransactionResponse;
import com.example.project.first.QuickPay.dto.TransactionResponseDto;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.repository.TransactionRepository;
import com.example.project.first.QuickPay.repository.UserRepository;
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
    private ModelMapper modelMapper;

    public TransactionResponse showTransaction(
            String username,
            Integer pageNumber,
            String sortPage,
            Integer pageSize,
            String sortOrder) {

        User user = userRepository.findByUsername(username).orElseThrow();


        Sort sortByAndOrder =
                sortOrder.equalsIgnoreCase("asc")?
                        Sort.by(sortPage).ascending() :
                        Sort.by(sortPage).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Transaction> transactionPage = transactionRepository.findAll(pageDetails);

        List<Transaction> allTransaction = transactionPage.getContent();
        if(allTransaction.isEmpty()) throw  new IllegalArgumentException("No Transaction Found");

//        List<Transaction> allTransactions = transactionRepository.findByUserId(user.getId());
//
//        if (allTransactions.isEmpty())
//            throw new IllegalArgumentException("No transactions found for user: " + username);
//
//        List<TransactionResponseDto> allResponseDtos = allTransactions
//                .stream()
//                .map( transaction -> new TransactionResponseDto(
//                        transaction.getMoneyStatus().toString(),
//                        transaction.getMoney(),
////                        Objects.equals(transaction.getUser().getUsername(), transaction.getUsername()) ? "Self":
//                                transaction.getUsername(),
//                        transaction.getAccNo().toString(),
//                        transaction.getTransactionTime()
//                        ))
//                .toList();


        List<TransactionResponseDto> transactionResponseDtos = allTransaction
                .stream()
                .map( transaction -> new TransactionResponseDto(
                        transaction.getMoneyStatus().toString(),
                        transaction.getMoney(),
//                        Objects.equals(transaction.getUser().getUsername(), transaction.getUsername()) ? "Self":
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
