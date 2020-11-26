package com.henriquebjr.sendmessage.api.v1.resource;

import com.henriquebjr.sendmessage.api.v1.dto.MessageDTO;
import com.henriquebjr.sendmessage.api.v1.mapper.MessageMapper;
import com.henriquebjr.sendmessage.model.Message;
import com.henriquebjr.sendmessage.service.MessageService;
import com.henriquebjr.sendmessage.service.SecurityService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/messages")
@RolesAllowed({"admin", "user"})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    @Inject
    MessageService messageService;

    @Inject
    SecurityService securityService;

    @Inject
    MessageMapper messageMapper;

    @GET
    public Response list(@Context SecurityContext securityContext) {
        return Response
                .ok(messageMapper.map(messageService.retrieveAll(securityService.getCurrentTenantId(securityContext))))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response get(@Context SecurityContext securityContext, @PathParam("id") String id) throws Exception{
        return Response
                .ok(messageMapper.map(messageService.retrieveById(securityService.getCurrentTenantId(securityContext), id)))
                .build();
    }

    @POST
    public Response insert(@Context SecurityContext securityContext, MessageDTO messageDTODTO) throws Exception {
        Message message = messageService.insert(securityService.getCurrentUser(securityContext), messageMapper.map(messageDTODTO));
        return Response
                .ok(messageMapper.map(message))
                .status(201)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response edit(@Context SecurityContext securityContext, @PathParam("id") String id, MessageDTO messageDTO) throws Exception {
        Message message = messageService.update(securityService.getCurrentUser(securityContext), id, messageMapper.map(messageDTO));
        return Response
                .ok(messageMapper.map(message))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@Context SecurityContext securityContext, @PathParam("id") String id) throws Exception {
        messageService.delete(securityService.getCurrentUser(securityContext), id);
        return Response.noContent().build();
    }
}
