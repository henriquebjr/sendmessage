package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Message;
import com.henriquebjr.sendmessage.model.MessageStatusEnum;
import com.henriquebjr.sendmessage.model.MessageTypeEnum;
import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.MessageRepository;
import com.henriquebjr.sendmessage.service.exception.MessageAddresseeMandatoryException;
import com.henriquebjr.sendmessage.service.exception.MessageNotFoundException;
import com.henriquebjr.sendmessage.service.exception.MessageTypeMandatoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class MessageServiceTest {

    public static final String SESSION_TENANT_ID = "TENANT-ID-400";

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    public void testRetrieveAll() {
        var messages = Arrays.asList(
                Message.Builder.of().message("ola").build(),
                Message.Builder.of().message("bye").build()
        );
        when(messageRepository.listAll("TENANT-ID-200")).thenReturn(messages);

        var result = messageService.retrieveAll("TENANT-ID-200");

        verify(messageRepository).listAll(eq("TENANT-ID-200"));
        assertThat(result).hasSize(2);
    }

    @Test
    public void testRetrieveById() throws Exception {
        var message = Message.Builder.of().message("ola").build();

        when(messageRepository.findByIdOptional("TENANT-ID-201", "MESSAGE-ID-10"))
                .thenReturn(Optional.of(message));

        var result = messageService.retrieveById("TENANT-ID-201", "MESSAGE-ID-10");

        verify(messageRepository).findByIdOptional(eq("TENANT-ID-201"), eq("MESSAGE-ID-10"));
        assertThat(result.getMessage()).isEqualTo("ola");
    }

    @Test
    public void testInsert() throws Exception {
        var sessionUser= User.Builder.of()
                .tenant(Tenant.Builder.of()
                        .id(SESSION_TENANT_ID)
                        .build())
                .name("user")
                .build();

        var message = Message.Builder.of()
                .message("ola como vai?")
                .type(MessageTypeEnum.EMAIL)
                .sendTo("11890842938403")
                .build();

        var result = messageService.insert(sessionUser, message);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getTenant().getId()).isEqualTo(SESSION_TENANT_ID);
        assertThat(result.getCreatedBy()).isEqualTo(sessionUser);
        assertThat(result.getCreatedDate()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(MessageStatusEnum.PENDING);
        verify(messageRepository).persist(eq(message));
    }

    @Test
    public void testInsertWhenTypeWasNotFilled() {
        var sessionUser= User.Builder.of()
                .tenant(Tenant.Builder.of()
                        .id(SESSION_TENANT_ID)
                        .build())
                .name("user")
                .build();

        var message = Message.Builder.of()
                .message("ola como vai?")
                .sendTo("11890842938403")
                .build();

        var result = catchThrowable(() -> messageService.insert(sessionUser, message));

        assertThat(result)
                .isExactlyInstanceOf(MessageTypeMandatoryException.class)
                .hasMessage("Message type is mandatory.");

        verify(messageRepository, never()).persist(eq(message));
    }

    @Test
    public void testInsertWhenSendToWasNotFilled() {
        var sessionUser= User.Builder.of()
                .tenant(Tenant.Builder.of()
                        .id(SESSION_TENANT_ID)
                        .build())
                .name("user")
                .build();

        var message = Message.Builder.of()
                .message("ola como vai?")
                .type(MessageTypeEnum.EMAIL)
                .build();

        var result = catchThrowable(() -> messageService.insert(sessionUser, message));

        assertThat(result)
                .isExactlyInstanceOf(MessageAddresseeMandatoryException.class)
                .hasMessage("Message addressee (sendTo) is mandatory.");

        verify(messageRepository, never()).persist(eq(message));
    }
    
    @Test
    public void testUpdate() throws Exception {
        var sessionUser= User.Builder.of()
                .tenant(Tenant.Builder.of()
                        .id(SESSION_TENANT_ID)
                        .build())
                .name("user")
                .build();

        var oldMessage = Message.Builder.of()
                .id("MESSAGE-ID-100")
                .message("hello")
                .type(MessageTypeEnum.SMS)
                .sendTo("11890842938403")
                .build();
        when(messageRepository.findByIdOptional(SESSION_TENANT_ID, "MESSAGE-ID-100"))
                .thenReturn(Optional.of(oldMessage));

        var newMessage = Message.Builder.of()
                .id("MESSAGE-ID-100")
                .message("ola como vai?")
                .type(MessageTypeEnum.EMAIL)
                .sendTo("11890842938403")
                .build();

        var result = messageService.update(sessionUser, "MESSAGE-ID-100", newMessage);

        assertThat(result.getMessage()).isEqualTo(newMessage.getMessage());
        assertThat(result.getScheduledDate()).isEqualTo(newMessage.getScheduledDate());
        assertThat(result.getSendTo()).isEqualTo(newMessage.getSendTo());
        assertThat(result.getType()).isEqualTo(newMessage.getType());
        assertThat(result.getUpdatedDate()).isNotNull();
    }

    @Test
    public void testUpdateWhenIdIsInvalid() throws Exception {
        var sessionUser= User.Builder.of()
                .tenant(Tenant.Builder.of()
                        .id(SESSION_TENANT_ID)
                        .build())
                .name("user")
                .build();
        when(messageRepository.findByIdOptional(SESSION_TENANT_ID, "MESSAGE-ID-100"))
                .thenReturn(Optional.empty());

        var message = Message.Builder.of()
                .id("MESSAGE-ID-100")
                .message("ola como vai?")
                .type(MessageTypeEnum.EMAIL)
                .sendTo("11890842938403")
                .build();

        var result = catchThrowable(() -> messageService.update(sessionUser, "MESSAGE-ID-100", message));

        assertThat(result).isExactlyInstanceOf(MessageNotFoundException.class)
            .hasMessage("Message not found. Id: MESSAGE-ID-100");
    }

    @Test
    public void testDelete() throws Exception {
        var sessionUser= User.Builder.of()
                .tenant(Tenant.Builder.of()
                        .id(SESSION_TENANT_ID)
                        .build())
                .name("user")
                .build();

        var message = Message.Builder.of()
                .id("MESSAGE-ID-150")
                .message("hello")
                .type(MessageTypeEnum.SMS)
                .sendTo("11890842938403")
                .build();
        when(messageRepository.findByIdOptional(SESSION_TENANT_ID, "MESSAGE-ID-150"))
                .thenReturn(Optional.of(message));

        messageService.delete(sessionUser, "MESSAGE-ID-150");

        verify(messageRepository).deleteById("MESSAGE-ID-150");
    }

    @Test
    public void testDeleteWhenDoesNotFoundMessage() throws Exception {
        var sessionUser= User.Builder.of()
                .tenant(Tenant.Builder.of()
                        .id(SESSION_TENANT_ID)
                        .build())
                .name("user")
                .build();

        when(messageRepository.findByIdOptional(SESSION_TENANT_ID, "MESSAGE-ID-151"))
                .thenReturn(Optional.empty());

        var result = catchThrowable(() -> messageService.delete(sessionUser, "MESSAGE-ID-151"));

        assertThat(result).isExactlyInstanceOf(MessageNotFoundException.class)
                .hasMessage("Message not found. Id: MESSAGE-ID-151");
    }
}
