package com.citybank.model;

import com.citybank.model.enums.AccountType;
import com.citybank.model.enums.Branch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

public class Account implements Serializable {

    private static final long serialVersionUID = 2L;

    private static final Integer fixedDeposit = 470;

    private String accountNumber;
    private String accountHolder;
    private Branch accountBranch;
    private AccountType accountType;
    private Double availBalance;
    private Double currentBalance;

    public Account(String accountHolder, Branch accountBranch, AccountType accountType, Double currentBalance) {
        this.accountHolder = accountHolder;
        this.accountBranch = accountBranch;
        this.accountType = accountType;
        this.currentBalance = currentBalance;
        this.accountNumber = String
                .format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))
                .substring(0,16);
        this.availBalance = currentBalance - fixedDeposit;
    }

    public Account() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    public void setAvailBalance(Double availBalance) {
        this.availBalance = availBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Branch getAccountBranch() {
        return accountBranch;
    }

    public void setAccountBranch(Branch accountBranch) {
        this.accountBranch = accountBranch;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getAvailBalance() {
        return availBalance;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
        this.availBalance = currentBalance - fixedDeposit;
    }
}
