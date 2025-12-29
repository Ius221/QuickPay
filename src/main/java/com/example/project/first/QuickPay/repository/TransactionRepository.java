package com.example.project.first.QuickPay.repository;

import com.example.project.first.QuickPay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    Optional<List<Transaction>> findByUserUsername(String username);

//    List<Transaction> findByUser_Username(String username);

    List<Transaction> findByUserId(Long id);
}
