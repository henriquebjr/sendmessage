package com.henriquebjr.sendmessage.api.v1.mapper;

import com.henriquebjr.sendmessage.api.v1.dto.UserDTO;
import com.henriquebjr.sendmessage.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    UserDTO map(User user);

    List<UserDTO> map(List<User> users);

    User map(UserDTO userDTO);
}
