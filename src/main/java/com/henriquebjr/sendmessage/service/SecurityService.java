package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.UserRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.SecurityContext;

import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;

@RequestScoped
public class SecurityService {

    @Inject
    UserRepository userRepository;

    @Transactional(NOT_SUPPORTED)
    public User getCurrentUser(SecurityContext securityContext) {
        //return userRepository.findByUsername(securityContext.getUserPrincipal().getName());
        return User.Builder.of().id("222feea8-c2ab-43a5-ae49-a250fad26bd0")
                .tenant(Tenant.Builder.of().id("48391fe7-36ab-4771-ae31-b84d86bd79d2").build())
                .build();
    }

    @Transactional(NOT_SUPPORTED)
    public String getCurrentTenantId(SecurityContext securityContext) {
        //return getCurrentUser(securityContext).getTenant().getId();
        return "48391fe7-36ab-4771-ae31-b84d86bd79d2";
    }
}
