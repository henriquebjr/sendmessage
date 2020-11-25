package com.henriquebjr.sendmessage.service.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String id) {
        super("User not found. Id: " + id);
    }
}
