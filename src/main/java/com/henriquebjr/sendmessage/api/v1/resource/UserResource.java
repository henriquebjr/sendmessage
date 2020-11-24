package com.henriquebjr.sendmessage.api.v1.resource;

import com.henriquebjr.sendmessage.api.v1.dto.UserDTO;
import com.henriquebjr.sendmessage.api.v1.mapper.UserMapper;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @Inject
    private UserMapper userMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response
                .ok(userMapper.map(userService.listAll()))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        return Response
                .ok(userMapper.map(userService.retrieveById(id)))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(UserDTO userDTO) {
        User user = userService.insert(userMapper.map(userDTO));
        return Response
                .ok(userMapper.map(user))
                .status(201)
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(UserDTO userDTO) {
        User user = userService.update(userMapper.map(userDTO));
        return Response
                .ok(userMapper.map(user))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        userService.delete(id);
        return Response.noContent().build();
    }
}
