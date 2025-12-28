package com.example.project.first.QuickPay.repository;

import com.example.project.first.QuickPay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
