package com.citybank.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.math.BigInteger;
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
    private String contactNumber;
    private Set<String> accounts;

    public AccountHolder() {
    }

    public AccountHolder(String NIC, String name, LocalDate dateOfBirth) {
        this.NIC = NIC;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.accounts = new HashSet<>();
        this.bankAssignedId = String
                .format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))
                .substring(0,16);
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

    public void setContactNumber(String contactNumber) {
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

    public String getContactNumber() {
        return contactNumber;
    }

    public Set<String> getAccounts() {
        return accounts;
    }

    public String getBankAssignedId() {
        return bankAssignedId;
    }

    public StringProperty getNICTableView() {
        return new SimpleStringProperty(NIC);
    }

    public StringProperty getNameTableView() {
        return new SimpleStringProperty(name);
    }

    public StringProperty getContactNumberTableView() {
        return new SimpleStringProperty(contactNumber);
    }

    public StringProperty getBankAssignedIdTableView() {
        return new SimpleStringProperty(bankAssignedId);
    }

    public StringProperty getAddressTableView() {
        return  new SimpleStringProperty(address);
    }
}
