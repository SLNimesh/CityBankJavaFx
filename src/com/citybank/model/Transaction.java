package com.citybank.model;

import com.citybank.model.enums.TransactionType;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Transaction {

    private LocalDateTime transactionDate;
    private String accountNo;
    private String description;
    private TransactionType type;
    private Double amount;
    private Double accountBalance;

    public Transaction() {

    }

    public Transaction(String accountNo, String description, Double amount, Double accountBalance, TransactionType transactionType) {
        this.accountNo = accountNo;
        if (description == null || description.equals("")) {
            this.description = "N/A";
        } else {
            this.description = description;
        }
        this.amount = amount;
        this.accountBalance = accountBalance;
        this.transactionDate = LocalDateTime.now(ZoneId.of("Asia/Colombo"));
        this.type = transactionType;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return type.name() + " OF " + amount + " ON " + transactionDate + "[ BAL: " + accountBalance + " ]";
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }
}
