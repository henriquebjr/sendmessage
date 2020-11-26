package com.henriquebjr.sendmessage.service.exception;

public class MessageAddresseeMandatoryException extends Exception{

    public MessageAddresseeMandatoryException() {
        super("Message addressee (sendTo) is mandatory.");
    }

}
