package com.henriquebjr.sendmessage.repository;

import com.henriquebjr.sendmessage.model.Message;
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
public class MessageRepository implements PanacheRepositoryBase<Message, String> {

    @Transactional(NOT_SUPPORTED)
    public List<Message> listAll(String tenantId) {
        return list("createdBy.tenant.id", tenantId);
    }

    @Transactional(NOT_SUPPORTED)
    public Optional<Message> findByIdOptional(String tenantId, String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("id", id);
        return find("createdBy.tenant.id = :tenantId and id = :id", params).firstResultOptional();
    }

}
