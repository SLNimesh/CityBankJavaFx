package com.citybank.model;

import com.citybank.model.enums.TransactionType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 7L;

    private String id;
    private LocalDateTime transactionDate;
    private String accountNo;
    private String description;
    private TransactionType type;
    private Double amount;
    private Double accountBalance;

    public Transaction() {

    }

    public Transaction(String accountNo, String description, Double amount, Double accountBalance, TransactionType transactionType) {
        this.id = String
                .format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))
                .substring(0,16);
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

    public TransactionType getType() {
        return type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a");
        return type.name() + " OF " + amount + " ON " + transactionDate.format(formatter) + "\t\t[ BAL: " + accountBalance + " ]";
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public StringProperty getIdTableView() {
        return new SimpleStringProperty(id);
    }

    public StringProperty getTransactionDateTableView() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a");
        return new SimpleStringProperty(transactionDate.format(formatter));
    }

    public String getAccountNo() {
        return accountNo;
    }

    public StringProperty getAccountNoTableView() {
        return new SimpleStringProperty(accountNo);
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public DoubleProperty getAmountTableView() {
        return new SimpleDoubleProperty(amount);
    }

    public Double getAccountBalance() {
        return accountBalance;
    }


    public DoubleProperty getAccountBalanceTableView() {
        return new SimpleDoubleProperty(accountBalance);
    }

    public StringProperty getTransactionTypeTableView() {
        return new SimpleStringProperty(type.name());
    }
}
