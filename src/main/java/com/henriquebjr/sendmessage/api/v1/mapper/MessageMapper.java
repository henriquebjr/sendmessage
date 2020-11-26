package com.henriquebjr.sendmessage.api.v1.mapper;

import com.henriquebjr.sendmessage.api.v1.dto.MessageDTO;
import com.henriquebjr.sendmessage.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface MessageMapper {

    // @Mapping(source = "messageDTO.scheduledDate", target = "scheduledDate", dateFormat ="yyyy-MM-dd HH:mm:ss")

    @Mappings({
            @Mapping(source = "messageDTO.type", target = "type")
    })
    Message map(MessageDTO messageDTO);


    MessageDTO map(Message message);

    List<MessageDTO> map(List<Message> messages);

}
