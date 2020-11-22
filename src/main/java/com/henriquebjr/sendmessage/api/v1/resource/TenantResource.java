package com.henriquebjr.sendmessage.api.v1.resource;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.service.TenantService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tenants")
public class TenantResource {

    @Inject
    private TenantService tenantService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void insert(Tenant tenant) {
        tenantService.insert(tenant);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tenant> list() {
        return tenantService.listAll();
    }

}
