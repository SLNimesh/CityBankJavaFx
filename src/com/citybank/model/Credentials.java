package com.citybank.model;

import java.io.Serializable;
import java.util.Base64;

public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Base64.Encoder ENCODER = Base64.getEncoder();
    private static Base64.Decoder DECODER = Base64.getDecoder();

    private String userName;
    private String password;
    private String bankAssignedId;

    public Credentials() {
    }

    public Credentials(String userName, String password, String bankAssignedId) {
        this.userName = userName;
        this.password = new String(ENCODER.encode(password.getBytes()));
        this.bankAssignedId = bankAssignedId;
    }

    public String getBankAssignedId() {
        return bankAssignedId;
    }

    public void setBankAssignedId(String bankAssignedId) {
        this.bankAssignedId = bankAssignedId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new String(ENCODER.encode(password.getBytes()));
    }

    public String decodePassword() {
        return new String(DECODER.decode(this.password.getBytes()));
    }
}
