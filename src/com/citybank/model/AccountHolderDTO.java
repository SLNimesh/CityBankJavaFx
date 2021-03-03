package com.citybank.model;

import java.time.LocalDate;
import java.util.Set;

public class AccountHolderDTO {

    private String bankAssignedId;
    private String NIC;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String contactNumber;
    private Set<Account> accounts;
    private Credentials credentials;

    public AccountHolderDTO(AccountHolder accountHolder, Credentials credentials) {
        this.bankAssignedId = accountHolder.getBankAssignedId();
        this.NIC = accountHolder.getNIC();
        this.name = accountHolder.getName();
        this.dateOfBirth = accountHolder.getDateOfBirth();
        this.address = accountHolder.getAddress();
        this.contactNumber = accountHolder.getContactNumber();
        this.credentials =credentials;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getBankAssignedId() {
        return bankAssignedId;
    }

    public String getNIC() {
        return NIC;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}
