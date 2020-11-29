package com.henriquebjr.sendmessage.repository;

import com.henriquebjr.sendmessage.model.Tenant;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlClientHelper;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class TenantRepository {

    @Inject
    private Pool client;

    private Tenant of(Row row) {
        return Tenant.Builder.of()
                .id(row.getString("ID"))
                .name(row.getString("NAME"))
                .active(row.getBoolean("ACTIVE"))
                .createdDate(getDatetime(row, "CREATED_DATE"))
                .updatedDate(getDatetime(row, "UPDATED_DATE"))
                .build();
    }

    private Date getDatetime(Row row, String columnName) {
        var localDateTime = row.getLocalDateTime(columnName);
        if(localDateTime != null) {
            return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
        }
        return null;
    }

    public List<Tenant> listAll() {
        return null;
    }

    public Tenant findById(String tenantId) {
        return null;
    }

    public Long count() {
        return 10L;
    }

    public Optional<Tenant> findByIdOptional(String tenantId) {
        return Optional.empty();
    }

    public void deleteById(String tenantId) {
    }

    public Uni<Tenant> persist(Tenant tenant) {

        /*
        var query = "INSERT INTO TENANT (ID, NAME, ACTIVE, CREATED_DATE, UPDATED_DATE) VALUES (?, ?, ?, ?, ?) ";
        return client.preparedQuery(query)
                .execute(Tuple.of(tenant.getId(), tenant.getName(), tenant.getActive(), tenant.getCreatedDate(),
                        tenant.getUpdatedDate()))
                .onFailure().invoke(failure -> System.out.println("Failed with " + failure.getMessage()))
                .onCancellation().invoke(() -> System.out.println("Downstream has cancelled the interaction"))
                .onItem().transform(i -> of(i.iterator().next()));

         */


        return client.begin().onItem(). produceUni(tx -> tx
                .preparedQuery("INSERT INTO TENANT (ID, NAME, ACTIVE, CREATED_DATE, UPDATED_DATE) VALUES ($1, $2, $3, $4, $5) ")
                    .execute(Tuple.of(tenant.getId(), tenant.getName(), tenant.getActive(), tenant.getCreatedDate(), tenant.getUpdatedDate()))
                .onItem().transform(i -> of(i.iterator().next())));

    }

}
