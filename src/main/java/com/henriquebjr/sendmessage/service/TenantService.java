package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.repository.TenantRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;

@RequestScoped
public class TenantService {

    @Inject
    private TenantRepository tenantRepository;

    @Transactional
    public Tenant insert(Tenant tenant) {
        tenant.setId(UUID.randomUUID().toString());
        tenantRepository.persist(tenant);

        return tenantRepository.findById(tenant.getId());
    }

    @Transactional
    public Tenant update(Tenant tenant) {
        Optional<Tenant> tenantOptional = tenantRepository.findByIdOptional(tenant.getId());
        if(tenantOptional.isEmpty()) {
            throw new RuntimeException("Tenant not found. Id: " + tenant.getId());
        }

        Tenant currentTenant = tenantOptional.get();
        currentTenant.setActive(tenant.getActive());
        currentTenant.setName(tenant.getName());

        return currentTenant;
    }

    @Transactional
    public void delete(String tenantId) {
        tenantRepository.deleteById(tenantId);
    }

    @Transactional(NOT_SUPPORTED)
    public List<Tenant> listAll() {
        return tenantRepository.listAll();
    }
}
