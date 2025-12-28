package com.example.project.first.QuickPay.service;

import com.example.project.first.QuickPay.dto.SelfDepositRequestDto;
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
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SelfDeposit {

    @Autowired private WalletRepository walletRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public SelfResponseDto depositMoney(SelfDepositRequestDto depositRequestDto, String username) {

        User currUser = userRepository.findByUsername(username).orElse(null);

        Wallet wallet =walletRepository.findById(depositRequestDto.getAccNo()).orElse(null);

        return userWalletValidation(currUser, wallet, depositRequestDto, false);
    }

    public SelfResponseDto withdrawFund(@Valid SelfDepositRequestDto selfDepositRequestDto, String username) {
        User currUser = userRepository.findByUsername(username).orElse(null);

        Wallet wallet = walletRepository.findById(selfDepositRequestDto.getAccNo()).orElse(null);

        return userWalletValidation(currUser, wallet, selfDepositRequestDto, true);
    }

    private SelfResponseDto userWalletValidation(
            User currUser,
            Wallet wallet,
            SelfDepositRequestDto depositRequestDto,
            boolean isWithdraw
    ) {
        if (wallet == null || currUser == null)
            throw new IllegalArgumentException("Invalid Credentials");

        if (wallet.getUser().getUsername() != currUser.getUsername())
            throw new IllegalArgumentException("Username not Match with account Number");

        if (isWithdraw) {
            boolean passMatch = passwordEncoder.matches(
                    depositRequestDto.getPassword(), currUser.getPassword());
            if (!passMatch)
                throw new IllegalArgumentException("Password didn't Match");

            if (wallet.getMoney() < depositRequestDto.getMoney())
                throw new IllegalArgumentException("Insufficient Balance");
        }

        Transaction transaction = new Transaction();
        transaction.setMoney(depositRequestDto.getMoney());
        transaction.setMoneyStatus(Status.DEPOSIT);
        transaction.setUsername(currUser.getUsername());
        transaction.setAccNo(wallet.getAccNo());
        transaction.setWallet(wallet);
        transaction.setUser(currUser);

        transactionRepository.save(transaction);

        wallet
                .setMoney(isWithdraw
                ? wallet.getMoney() - depositRequestDto.getMoney()
                : wallet.getMoney() + depositRequestDto.getMoney());

        wallet = walletRepository.save(wallet);

        return new SelfResponseDto(wallet.getUser().getUsername(),
                wallet.getMoney(), wallet.getAccNo(), LocalDateTime.now());
    }
}
