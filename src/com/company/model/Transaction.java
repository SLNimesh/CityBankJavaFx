package com.company.model;

import com.company.model.enums.TransactionType;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Transaction {

    private LocalDateTime transactionDate;
    private Integer accountNo;
    private String description;
    private TransactionType type;
    private Double amount;
    private Double accountBalance;

    public Transaction(Integer accountNo, String description, Double amount, Double accountBalance, TransactionType transactionType) {
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

    @Override
    public String toString() {
        return type.name() + " OF " + amount + " ON " + transactionDate + "[ BAL: " + accountBalance + " ]";
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Integer getAccountNo() {
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
