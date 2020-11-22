package com.henriquebjr.sendmessage.repository;

import com.henriquebjr.sendmessage.model.Message;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MessageRepository implements PanacheRepositoryBase<Message, String> {

}
