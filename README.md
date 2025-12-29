
# 💸 QuickPay: Secure P2P Payment API

> A robust, transactional backend service facilitating secure digital wallet management and peer-to-peer fund transfers.

## 📖 Overview

QuickPay is a backend API simulation of a payment provider (similar to PayPal or Venmo). It demonstrates high-level competency in **System Design**, **ACID compliance**, and **API Security**.

The core focus of this project is ensuring transactional integrity—ensuring money is never created or destroyed during transfers, only moved securely between immutable records.

## 🛠 Tech Stack

### Core Backend

* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Data Access:** Spring Data JPA / Hibernate
* **Database:** MySQL (Local) / TiDB (Production/Cloud)
* **Security:** Spring Security 6 + JWT (Stateless Authentication)

### DevOps & Deployment

* **Containerization:** Docker
* **Cloud Hosting:** Render (Web Service)
* **Cloud Database:** TiDB Serverless

## ✨ Key Features

1. **Bank-Grade Security:**
* Stateless **JWT (JSON Web Token)** Authentication.
* Password encryption using **BCrypt**.
* Custom `UserDetailsService` implementation.


2. **Transactional Integrity (ACID):**
* Uses `@Transactional` to ensure atomicity.
* Prevents race conditions during concurrent transfers (e.g., stopping "double spending").
* Rolls back entire operations if any step fails.


3. **Wallet Logic:**
* Auto-provisioning of wallets upon user registration.
* Business logic to prevent overdrafts (Negative Balance Protection).


4. **Audit & History:**
* Immutable transaction logs for every Deposit, Withdrawal, and Peer-to-Peer Transfer.



## 🔌 API Endpoints (Quick Reference)

| Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `POST` | `/api/v1/auth/signup` | Register a new user & create wallet | ❌ |
| `POST` | `/api/v1/auth/login` | Authenticate & receive JWT Token | ❌ |
| `GET` | `/api/v1/csrf/generate` | Generate a CSRF Token | ❌ |
| `POST` | `/api/v1/show/balance` | Check current balance | ✅ |
| `POST` | `/api/v1/transfer/other` | Send money to another user | ✅ |
| `GET` | `/api/v1/show/transaction` | Get past transaction logs | ✅ |

*(Note: Protected endpoints require a valid Bearer Token in the `Authorization` header)*

## 🚀 How to Run Locally

### Prerequisites

* JDK 17 or higher
* Maven 3.x
* MySQL (or use H2/TiDB config)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/Ius221/QuickPay.git
cd QuickPay

```


2. **Configure Database**
Update `src/main/resources/application.properties` with your database credentials.
3. **Build & Run**
```bash
mvn clean install
mvn spring-boot:run

```


The API will be available at `http://localhost:8080`.

## ☁️ Deployment

This project is containerized using **Docker** and deployed on **Render**.

* **Live URL:** `https://quickpay-ucpg.onrender.com`
* **Database:** Connected to a remote **TiDB Serverless** cluster.

---

*Created as a Capstone Project to demonstrate proficiency in Backend Java Development and Cloud Deployment.*
