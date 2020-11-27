package com.henriquebjr.sendmessage.service.exception;

public class TenantNotFoundException extends Exception{

    public TenantNotFoundException(String id) {
        super("Tenant not found. Id: " + id);
    }
}
