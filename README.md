# 💸 QuickPay: Secure P2P Payment Platform

> A full-stack financial dashboard allowing users to manage digital wallets and transfer funds securely.

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green)
![Vue.js](https://img.shields.io/badge/Vue.js-3.0-brightgreen)
![Security](https://img.shields.io/badge/Security-JWT_RBAC-red)

## 📖 Overview
QuickPay is a simulation of a payment provider (like PayPal or Venmo). It demonstrates high-level competency in handling ACID transactions, secure authentication, and reactive frontend state management. The goal is to ensure that money is never created or destroyed during transfer—only moved.

## 🛠 Tech Stack

### Backend (The Vault)
* **Core:** Java 17+, Spring Boot 3.x
* **Database:** MySQL (H2 for testing)
* **ORM:** Hibernate / Spring Data JPA
* **Security:** Spring Security 6 + JWT (JSON Web Tokens)
* **Validation:** Hibernate Validator 

### Frontend (The Teller)
* **Framework:** Vue 3 (Composition API)
* **Routing:** Vue Router
* **Styling:** Tailwind CSS 
* **HTTP Client:** Axios (with Interceptors for Bearer Token injection)

## ✨ Key Features

1.  **Bank-Grade Security:**
    * Stateless JWT Authentication.
    * CSRF Safe 
    * Password Hashing with BCrypt.
    * Role-Based Access Control (Admin vs. User).
2.  **Wallet Management:**
    * Auto-generated wallet upon registration.
    * Real-time balance updates.
3.  **Transactional Integrity:**
    * Atomic money transfers (Sender -$$ -> Receiver +$$).
    * Prevention of race conditions (e.g., spending the same $50 twice).
4.  **Audit Logs:**
    * Complete transaction history (Withdraw/Deposit/Transfer).

## 🗄️ Database Schema (Simplified)

* **Users:** `id`, `username`, `password`, 
* **Wallets:** `id`, `user_id`, `balance`,
* **Transactions:** `id`, `sender_wallet_id`, `receiver_wallet_id`, `amount`, `timestamp`, `status`, `type`


### Prerequisites
* JDK 17 or higher
* Vue.js & npm
  


---
*Created as a Capstone Project to demonstrate proficiency in Full Stack Java Development.*
