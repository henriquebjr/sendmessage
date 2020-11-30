package com.henriquebjr.sendmessage.api.v1.resource;

import com.henriquebjr.sendmessage.api.v1.dto.UserDTO;
import com.henriquebjr.sendmessage.api.v1.mapper.UserMapper;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.service.SecurityService;
import com.henriquebjr.sendmessage.service.UserService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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

@Path("/users")
@RolesAllowed("admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "User")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    UserMapper userMapper;

    @Inject
    SecurityService securityService;

    @GET
    public Response list(@Context SecurityContext securityContext) {
        return Response
                .ok(userMapper.map(userService.retrieveAll(securityService.getCurrentTenantId(securityContext))))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response get(@Context SecurityContext securityContext, @PathParam("id") String id) {
        return Response
                .ok(userMapper.map(userService.retrieveById(securityService.getCurrentTenantId(securityContext), id)))
                .build();
    }

    @POST
    public Response insert(@Context SecurityContext securityContext, @RequestBody UserDTO userDTO) throws Exception {
        User user = userService.insert(securityService.getCurrentTenantId(securityContext), userMapper.map(userDTO));
        return Response
                .ok(userMapper.map(user))
                .status(201)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response edit(@Context SecurityContext securityContext, @PathParam("id") String id, @RequestBody UserDTO userDTO) throws Exception {
        User user = userService.update(securityService.getCurrentTenantId(securityContext), id, userMapper.map(userDTO));
        return Response
                .ok(userMapper.map(user))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@Context SecurityContext securityContext, @PathParam("id") String id) throws Exception {
        userService.delete(securityService.getCurrentTenantId(securityContext), id);
        return Response.noContent().build();
    }
}
