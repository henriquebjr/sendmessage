package com.henriquebjr.sendmessage.api.v1.resource;

import com.henriquebjr.sendmessage.api.v1.dto.TenantDTO;
import com.henriquebjr.sendmessage.api.v1.mapper.TenantMapper;
import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.service.TenantService;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tenants")
@RolesAllowed("admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TenantResource {

    @Inject
    TenantService tenantService;

    @Inject
    TenantMapper tenantMapper;

    @GET
    public Response list() {
        return Response
                .ok(tenantMapper.map(tenantService.retrieveAll()))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        return Response
                .ok(tenantMapper.map(tenantService.retrieveById(id)))
                .build();
    }

    @POST
    public Response insert(TenantDTO tenantDTO) throws Exception {
        Tenant tenant = tenantService.insert(tenantMapper.map(tenantDTO));
        return Response
                .ok(tenantMapper.map(tenant))
                .status(201)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response edit(@PathParam("id") String id, TenantDTO tenantDTO) {
        Tenant tenant = tenantService.update(id, tenantMapper.map(tenantDTO));
        return Response
                .ok(tenantMapper.map(tenant))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        tenantService.delete(id);
        return Response.noContent().build();
    }
}
