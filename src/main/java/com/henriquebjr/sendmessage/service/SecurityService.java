package com.henriquebjr.sendmessage.service;

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
        return userRepository.findByUsername(securityContext.getUserPrincipal().getName());
    }

    @Transactional(NOT_SUPPORTED)
    public String getCurrentTenantId(SecurityContext securityContext) {
        return getCurrentUser(securityContext).getTenant().getId();
    }
}
