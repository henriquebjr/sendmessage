package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.UserRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Transactional
    public User insert(Tenant tenant, User user) {
        user.setId(UUID.randomUUID().toString());
        user.setTenant(tenant);
        userRepository.persist(user);

        return userRepository.findById(user.getId());
    }

    @Transactional
    public User update(Tenant tenant, User user) {
        Optional<User> userOptional = userRepository.findByIdOptional(user.getId());
        if(userOptional.isEmpty() || !userOptional.get().getTenant().getId().equals(tenant.getId())) {
            throw new RuntimeException("User not found. Id: " + user.getId());
        }

        User currentUser = userOptional.get();

        currentUser.setTenant(tenant);
        currentUser.setActive(user.getActive());
        currentUser.setName(user.getName());

        return currentUser;
    }

    @Transactional
    public void delete(Tenant tenant, User user) {
        Optional<User> userOptional = userRepository.findByIdOptional(user.getId());
        if(userOptional.isEmpty() || !user.getTenant().getId().equals(tenant.getId())) {
            throw new RuntimeException("User not found. Id: " + user.getId());
        }

        userRepository.delete(user);
    }
}
