package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.UserRepository;
import com.henriquebjr.sendmessage.service.exception.UserInvalidRoleException;
import com.henriquebjr.sendmessage.service.exception.UserNotFoundException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> retrieveAll(String tenantId) {
        return userRepository.listAll(tenantId);
    }

    public User retrieveById(String tenantId, String id) {
        return userRepository.findById(tenantId, id);
    }

    public User insert(String tenantId, User user) throws Exception {
        verifyUserRole(user);

        user.setId(UUID.randomUUID().toString());
        user.setTenant(new Tenant(tenantId));
        user.setCreatedDate(new Date());
        user.setActive(user.getActive() == null || user.getActive());
        //user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        user.setPassword(user.getPassword()); // FIXME: add crypt hash here
        user.setRole(user.getRole());

        userRepository.persist(user);

        return user;
    }

    private void verifyUserRole(User user) throws UserInvalidRoleException {
        if(user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("user");
        } else if(!user.getRole().equals("admin") && !user.getRole().equals("user")){
            throw new UserInvalidRoleException();
        }
    }

    public User update(String tenantId, String id, User user) throws Exception {
        verifyUserRole(user);

        Optional<User> userOptional = userRepository.findByIdOptional(id);
        if(userOptional.isEmpty() || !userOptional.get().getTenant().getId().equals(tenantId)) {
            throw new UserNotFoundException(id);
        }

        User currentUser = userOptional.get();

        currentUser.setActive(user.getActive());
        currentUser.setName(user.getName());
        currentUser.setRole(user.getRole());

        if(user.getPassword() != null) {
            //currentUser.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
            currentUser.setPassword(user.getPassword()); // FIXME: add crypt hash here
        }

        return currentUser;
    }

    public void delete(String tenantId, String id) throws Exception {
        Optional<User> userOptional = userRepository.findByIdOptional(id);
        if(userOptional.isEmpty() || !userOptional.get().getTenant().getId().equals(tenantId)) {
            throw new UserNotFoundException(id);
        }

        userRepository.delete(id);
    }
}
