package com.henriquebjr.sendmessage.service.exception;

public class MessageTypeMandatoryException extends Exception {
    public MessageTypeMandatoryException() {
        super("Message type is mandatory.");
    }
}
