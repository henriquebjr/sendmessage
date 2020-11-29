package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Message;
import com.henriquebjr.sendmessage.model.MessageStatusEnum;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.MessageRepository;
import com.henriquebjr.sendmessage.service.exception.MessageAddresseeMandatoryException;
import com.henriquebjr.sendmessage.service.exception.MessageTypeMandatoryException;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;

@RequestScoped
public class MessageService {

    @Inject
    MessageRepository messageRepository;

    public Multi<Message> retrieveAll(String tenantId) {
        return messageRepository.listAll(tenantId);
    }

    public Uni<Message> retrieveById(String tenantId, String id) throws Exception{
        return messageRepository.findByIdOptional(tenantId, id);
    }

    public Message insert(User currentUser, Message message) throws Exception{
        validate(message);

        message.setId(UUID.randomUUID().toString());
        message.setTenant(currentUser.getTenant());
        message.setCreatedBy(currentUser);
        message.setCreatedDate(new Date());
        message.setStatus(MessageStatusEnum.PENDING);
        messageRepository.persist(message);

        return message;
    }

    private void validate(Message message) throws MessageTypeMandatoryException, MessageAddresseeMandatoryException {
        if(message.getType() == null) {
            throw new MessageTypeMandatoryException();
        }

        if(message.getSendTo() == null) {
            throw new MessageAddresseeMandatoryException();
        }
    }

    public Message update(User currentUser, String id, Message message) throws Exception {
        /*validate(message);

        Optional<Message> messageOptional = messageRepository.findByIdOptional(currentUser.getTenant().getId(), id);
        if(messageOptional.isEmpty()) {
            throw new MessageNotFoundException(id);
        }

        Message currentMessage = messageOptional.get();
        currentMessage.setMessage(message.getMessage());
        currentMessage.setScheduledDate(message.getScheduledDate());
        currentMessage.setSendTo(message.getSendTo());
        currentMessage.setType(message.getType());
        currentMessage.setUpdatedDate(new Date());

        return currentMessage;*/
        return null;
    }

    public void delete(User currentUser, String id) throws Exception {
        /*
        Optional<Message> messageOptional = messageRepository.findByIdOptional(currentUser.getTenant().getId(), id);
        if(messageOptional.isEmpty()) {
            throw new MessageNotFoundException(id);
        }
         */

        //messageRepository.deleteById(id);
    }
}
