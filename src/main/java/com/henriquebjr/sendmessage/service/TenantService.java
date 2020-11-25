package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.TenantRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;

@RequestScoped
public class TenantService {

    @Inject
    TenantRepository tenantRepository;

    @Inject
    UserService userService;

    @Transactional
    public Tenant insert(Tenant tenant) throws Exception {
        tenant.setId(UUID.randomUUID().toString());
        tenant.setCreatedDate(new Date());
        tenant.setActive(tenant.getActive() == null || tenant.getActive());
        tenantRepository.persistAndFlush(tenant);

        createTenantAdmin(tenant);

        return tenant;
    }

    private void createTenantAdmin(Tenant tenant) throws Exception {
        var username = tenant.getName().equals("default") ? "admin"
            : "admin_" + tenant.getName();
        User user = User.Builder.of()
                .name(username)
                .username(username)
                .password("123")
                .role("admin")
                .build();
        userService.insert(tenant.getId(), user);
    }

    @Transactional
    public Tenant update(String tenantId, Tenant tenant) {
        Optional<Tenant> tenantOptional = tenantRepository.findByIdOptional(tenantId);
        if(tenantOptional.isEmpty()) {
            throw new RuntimeException("Tenant not found. Id: " + tenant.getId());
        }

        Tenant currentTenant = tenantOptional.get();
        currentTenant.setActive(tenant.getActive());
        currentTenant.setName(tenant.getName());
        currentTenant.setUpdatedDate(new Date());

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

    @Transactional(NOT_SUPPORTED)
    public Tenant retrieveById(String tenantId) {
        return tenantRepository.findById(tenantId);
    }

    @Transactional(NOT_SUPPORTED)
    public Long countAll() {
        return tenantRepository.count();
    }
}
