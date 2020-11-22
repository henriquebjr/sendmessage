package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Message;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.MessageRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@RequestScoped
public class MessageService {

    @Inject
    private MessageRepository messageRepository;

    @Transactional
    public Message insert(User user, Message message) {
        message.setId(UUID.randomUUID().toString());
        message.setCreatedBy(user);
        messageRepository.persist(message);

        return messageRepository.findById(message.getId());
    }
}
