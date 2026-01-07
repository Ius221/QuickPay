package com.example.project.first.QuickPay.repository;

import com.example.project.first.QuickPay.entity.Transaction;
import com.example.project.first.QuickPay.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    Optional<List<Transaction>> findByUserUsername(String username);

//    List<Transaction> findByUser_Username(String username);
    Page<Transaction> findByUser(User user, Pageable pageable);
    List<Transaction> findByUserId(Long id);
}
