package com.henriquebjr.sendmessage.repository;

import com.henriquebjr.sendmessage.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;

@RequestScoped
public class UserRepository implements PanacheRepositoryBase<User, String> {

    @Transactional(NOT_SUPPORTED)
    public List<User> listAll(String tenantId) {
        return list("tenant.id", tenantId);
    }

    @Transactional(NOT_SUPPORTED)
    public User findById(String tenantId, String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("id", id);
        return find("tenant.id = :tenantId and id = :id", params).firstResult();
    }

    @Transactional(NOT_SUPPORTED)
    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }

}
