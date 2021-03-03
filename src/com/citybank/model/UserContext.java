package com.citybank.model;

import com.citybank.model.enums.UserRole;

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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContactNumber() {
        return contactNumber;
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
}
