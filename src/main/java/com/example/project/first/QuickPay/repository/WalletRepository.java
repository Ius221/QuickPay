package com.example.project.first.QuickPay.repository;

import com.example.project.first.QuickPay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
