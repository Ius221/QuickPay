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

    public MoneyTransferResponseDto transferMoney(String username, MoneyTransferRequestDto moneyTransferRequestDto){

        User currUser = userRepository.findByUsername(username).orElseThrow();

        Wallet receiverWallet = walletRepository.findById(moneyTransferRequestDto.getAccNo()).orElse(null);

        Wallet senderWallet = walletRepository.findById(currUser.getWallet().getAccNo()).orElse(null);

        if(!passwordEncoder.matches(moneyTransferRequestDto.getPassword(),currUser.getPassword()))
            throw new IllegalArgumentException("Password doesn't match");

        if(currUser.getWallet().getMoney() < moneyTransferRequestDto.getMoney())
            throw new IllegalArgumentException(
                "Insufficient Balance");

        if(currUser.getWallet().getAccNo() == moneyTransferRequestDto.getAccNo())
            throw new IllegalArgumentException("Check Account Number");

        if(receiverWallet == null || senderWallet == null)
            throw new IllegalArgumentException("Account number is not " +
                "Correct");

        double currUserMoney = currUser.getWallet().getMoney() - moneyTransferRequestDto.getMoney();
        receiverWallet.setMoney(receiverWallet.getMoney() + moneyTransferRequestDto.getMoney());
        senderWallet.setMoney(currUserMoney);

        walletRepository.save(receiverWallet);
        walletRepository.save(senderWallet);

        Transaction sendMoney = new Transaction();
        sendMoney.setMoney(moneyTransferRequestDto.getMoney());
        sendMoney.setMoneyStatus(Status.SEND);
        sendMoney.setUsername(receiverWallet.getUser().getUsername());
        sendMoney.setAccNo(receiverWallet.getAccNo());
        sendMoney.setUser(currUser);
        sendMoney.setWallet(currUser.getWallet());

        transactionRepository.save(sendMoney);

        Transaction receiveMoney = new Transaction();
        receiveMoney.setMoney(moneyTransferRequestDto.getMoney());
        receiveMoney.setMoneyStatus(Status.RECEIVE);
        receiveMoney.setUsername(senderWallet.getUser().getUsername());
        receiveMoney.setAccNo(senderWallet.getAccNo());
        receiveMoney.setUser(receiverWallet.getUser());
        receiveMoney.setWallet(receiverWallet);

        transactionRepository.save(receiveMoney);

        MoneyTransferResponseDto moneyTransferResponseDto = new MoneyTransferResponseDto();
        moneyTransferResponseDto.setCurrBalance(currUserMoney);
        moneyTransferResponseDto.setStatus("SUCCESS");

        return moneyTransferResponseDto;

    }
}
