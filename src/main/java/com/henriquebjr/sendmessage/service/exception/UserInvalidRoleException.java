package com.henriquebjr.sendmessage.service.exception;

public class UserInvalidRoleException extends Exception {

    public UserInvalidRoleException() {
        super("Invalid user role.");
    }
}
