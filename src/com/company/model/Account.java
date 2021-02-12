package com.company.model;

import com.company.model.enums.AccountType;
import com.company.model.enums.Branch;

public class Account {

    private static Integer accNumCount = Integer.MAX_VALUE - 100000000;
    private static final Integer fixedDeposit = 470;

    private Integer accountNumber;
    private AccountHolder accountHolder;
    private Branch accountBranch;
    private AccountType accountType;
    private Double availBalance;
    private Double currentBalance;

    public Account(AccountHolder accountHolder, Branch accountBranch, AccountType accountType, Double currentBalance) {
        this.accountHolder = accountHolder;
        this.accountBranch = accountBranch;
        this.accountType = accountType;
        this.currentBalance = currentBalance;
        this.accountNumber = accNumCount;
        this.availBalance = currentBalance - fixedDeposit;
        accNumCount++;
    }

    public Account() {
    }

    public void setAvailBalance(Double availBalance) {
        this.availBalance = availBalance;
    }

    public static Integer getAccNumCount() {
        return accNumCount;
    }

    public static void setAccNumCount(Integer accNumCount) {
        Account.accNumCount = accNumCount;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
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
