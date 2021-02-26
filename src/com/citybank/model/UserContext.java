package com.citybank.model;

import java.io.Serializable;

public class UserContext implements Serializable {

    private static final long serialVersionUID = 4L;

    private String bankAssignedID;
    private String firstName;
    private String lastName;
    private String nic;
    private Integer contactNumber;
    private String role;

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

    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
