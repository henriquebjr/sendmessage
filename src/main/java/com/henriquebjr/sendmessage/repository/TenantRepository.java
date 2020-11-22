package com.henriquebjr.sendmessage.repository;

import com.henriquebjr.sendmessage.model.Tenant;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TenantRepository implements PanacheRepositoryBase<Tenant, String> {

}
