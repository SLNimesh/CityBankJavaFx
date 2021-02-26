package com.citybank.model;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
