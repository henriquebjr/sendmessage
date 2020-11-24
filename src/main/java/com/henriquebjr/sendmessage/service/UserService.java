package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.UserRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;

@RequestScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Transactional
    public User insert(User user) {
        user.setId(UUID.randomUUID().toString());
        userRepository.persist(user);

        return userRepository.findById(user.getId());
    }

    @Transactional
    public User update(User user) {
        Optional<User> userOptional = userRepository.findByIdOptional(user.getId());
        if(userOptional.isEmpty()) {
            throw new RuntimeException("User not found. Id: " + user.getId());
        }

        User currentUser = userOptional.get();

        currentUser.setActive(user.getActive());
        currentUser.setName(user.getName());

        return currentUser;
    }

    @Transactional
    public void delete(String id) {
        Optional<User> userOptional = userRepository.findByIdOptional(id);
        if(userOptional.isEmpty()) {
            throw new RuntimeException("User not found. Id: " + id);
        }

        userRepository.delete(id);
    }

    @Transactional(NOT_SUPPORTED)
    public List<User> listAll() {
        return userRepository.listAll();
    }

    @Transactional(NOT_SUPPORTED)
    public User retrieveById(String userId) {
        return userRepository.findById(userId);
    }
}
