package com.citybank.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AccountHolder implements Serializable {

    private static final long serialVersionUID = 3L;

    private String bankAssignedId;
    private String NIC;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private Integer contactNumber;
    private Set<String> accounts;

    public AccountHolder() {
    }

    public AccountHolder(String NIC, String name, LocalDate dateOfBirth) {
        this.NIC = NIC;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.accounts = new HashSet<>();
        this.bankAssignedId = UUID.randomUUID().toString();
    }

    public void setBankAssignedId(String bankAssignedId) {
        this.bankAssignedId = bankAssignedId;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAccounts(Set<String> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(String accNumber) {
        this.accounts.add(accNumber);
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

    public Integer getContactNumber() {
        return contactNumber;
    }

    public Set<String> getAccounts() {
        return accounts;
    }

    public String getBankAssignedId() {
        return bankAssignedId;
    }
}
