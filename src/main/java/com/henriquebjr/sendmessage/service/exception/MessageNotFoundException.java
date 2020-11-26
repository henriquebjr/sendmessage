package com.henriquebjr.sendmessage.service.exception;

import java.io.Serializable;

public class MessageNotFoundException extends Exception implements Serializable {

    public MessageNotFoundException(String id) {
        super("Message not found. Id: " + id);
    }
}
