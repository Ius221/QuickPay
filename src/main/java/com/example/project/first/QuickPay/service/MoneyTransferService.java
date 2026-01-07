package com.example.project.first.QuickPay.service;

import com.example.project.first.QuickPay.dto.MoneyTransferRequestDto;
import com.example.project.first.QuickPay.dto.MoneyTransferResponseDto;
import com.example.project.first.QuickPay.dto.TransactionResponseDto;
import com.example.project.first.QuickPay.entity.Status;
import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.entity.User;
import com.example.project.first.QuickPay.entity.Wallet;
import com.example.project.first.QuickPay.repository.TransactionRepository;
import com.example.project.first.QuickPay.repository.UserRepository;
import com.example.project.first.QuickPay.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public MoneyTransferResponseDto transferMoney( MoneyTransferRequestDto moneyTransferRequestDto){

    User currUser = userRepository.findByEmail(moneyTransferRequestDto.getEmail()).orElse(null);
    if(currUser == null) throw new IllegalArgumentException("User not found");

    Wallet otherWallet = walletRepository.findById(moneyTransferRequestDto.getAccNo()).orElse(null);
    if(otherWallet == null) throw new IllegalArgumentException("Account doesn't exists");

    Wallet currWallet = currUser.getWallet();
    if(moneyTransferRequestDto.getMoney() > currWallet.getMoney()) throw new IllegalArgumentException("Insufficient " +
            "Balance");

    if(currWallet.getAccNo() == moneyTransferRequestDto.getAccNo()) throw  new IllegalArgumentException("Can't " +
            "perform this operation on same account");


    currWallet.setMoney(currWallet.getMoney() - moneyTransferRequestDto.getMoney());
    otherWallet.setMoney(otherWallet.getMoney() + moneyTransferRequestDto.getMoney());

    Transaction senderTransaction = new Transaction();
    senderTransaction.setMoney(moneyTransferRequestDto.getMoney());
    senderTransaction.setMoneyStatus(Status.SEND);
    senderTransaction.setSenderName(currUser.getUsername());
    senderTransaction.setReceiverName(otherWallet.getUser().getUsername());
    senderTransaction.setReceiverAccNo(otherWallet.getAccNo());
    senderTransaction.setSenderAccNo(currWallet.getAccNo());
    senderTransaction.setUser(currUser);

    Transaction receiverTransaction = new Transaction();
    receiverTransaction.setMoney(moneyTransferRequestDto.getMoney());
    receiverTransaction.setMoneyStatus(Status.RECEIVE);
    receiverTransaction.setSenderName(currUser.getUsername());
    receiverTransaction.setReceiverName(otherWallet.getUser().getUsername());
    receiverTransaction.setReceiverAccNo(otherWallet.getAccNo());
    receiverTransaction.setSenderAccNo(currWallet.getAccNo());
    receiverTransaction.setUser(otherWallet.getUser());

    otherWallet = walletRepository.save(otherWallet);
    currWallet = walletRepository.save(currWallet);
    transactionRepository.save(senderTransaction);
    transactionRepository.save(receiverTransaction);

    return new MoneyTransferResponseDto(currWallet.getMoney(), "Success");
    }
}
