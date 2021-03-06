package com.citybank.model;

import com.citybank.BankService;
import com.citybank.model.enums.UserRole;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.UUID;

public class UserContext implements Serializable {

    private static final long serialVersionUID = 4L;

    private String bankAssignedID;
    private String firstName;
    private String lastName;
    private String nic;
    private String contactNumber;
    private String role;

    public UserContext() {
    }

    public UserContext(String firstName, String lastName, String nic, String contactNumber, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.contactNumber = contactNumber;
        this.role = role.name();
        this.bankAssignedID = UUID.randomUUID().toString();
    }

    public String getBankAssignedID() {
        return bankAssignedID;
    }

    public StringProperty getBankAssignedIDTable() {
        return new SimpleStringProperty(bankAssignedID);
    }

    public void setBankAssignedID(String bankAssignedID) {
        this.bankAssignedID = bankAssignedID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public StringProperty getFullNameTable() {
        return new SimpleStringProperty(firstName.concat(" ").concat(lastName));
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNic() {
        return nic;
    }

    public StringProperty getNICTable() {
        return new SimpleStringProperty(nic);
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public StringProperty getContactNumberTable() {
        return new SimpleStringProperty(contactNumber);
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public StringProperty getUserName() {
        return new SimpleStringProperty(BankService.findCredentials(bankAssignedID).getUserName());
    }
}
