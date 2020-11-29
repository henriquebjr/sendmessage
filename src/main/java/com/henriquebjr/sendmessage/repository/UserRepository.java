package com.henriquebjr.sendmessage.repository;

import com.henriquebjr.sendmessage.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;

@RequestScoped
public class UserRepository {

    @Transactional(NOT_SUPPORTED)
    public List<User> listAll(String tenantId) {
        //return list("tenant.id", tenantId);

        return null;
    }

    @Transactional(NOT_SUPPORTED)
    public User findById(String tenantId, String id) {
        /*
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("id", id);
        return find("tenant.id = :tenantId and id = :id", params).firstResult();

         */

        return null;
    }

    @Transactional(NOT_SUPPORTED)
    public User findByUsername(String username) {
        //return find("username", username).firstResult();

        return null;
    }

    public void persist(User user) {
    }

    public Optional<User> findByIdOptional(String id) {
        return Optional.empty();
    }

    public void delete(String id) {
    }
}
