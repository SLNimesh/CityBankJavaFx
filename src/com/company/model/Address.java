package com.company.model;

public class Address {

    private String lineOne;
    private String lineTwo;
    private String city;
    private Integer postalCode;

    public Address(String lineOne, String lineTwo, String city, Integer postalCode) {
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.city = city;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return lineOne + "," + lineTwo + "," + city + "," + postalCode;
    }

    public String getLineOne() {
        return lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public String getCity() {
        return city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }
}
