package com.company.model;

import java.util.Base64;

public class Credentials {

    private static Base64.Encoder ENCODER = Base64.getEncoder();
    private static Base64.Decoder DECODER = Base64.getDecoder();

    private String userName;
    private String password;

    public Credentials(String userName, String password) {
        this.userName = userName;
        this.password = new String(ENCODER.encode(password.getBytes()));
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
