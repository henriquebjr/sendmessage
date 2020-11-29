package com.henriquebjr.sendmessage.repository;

import com.henriquebjr.sendmessage.model.Message;
import com.henriquebjr.sendmessage.model.MessageStatusEnum;
import com.henriquebjr.sendmessage.model.MessageTypeEnum;
import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;

@RequestScoped
public class MessageRepository {

    @Inject
    private Pool client;

    public Message of(Row row) {
        return Message.Builder.of()
                .id(row.getString("ID"))
                .tenant(Tenant.Builder.of().id(row.getString("TENANT_ID")).build())
                .createdBy(User.Builder.of().id(row.getString("CREATED_BY")).build())
                .message(row.getString("MESSAGE"))
                .sendTo(row.getString(("SEND_TO")))
                .type(MessageTypeEnum.valueOf(row.getString("TYPE")))
                .status(MessageStatusEnum.valueOf(row.getString("STATUS")))
                .scheduledDate(getDatetime(row, "SCHEDULED_DATE"))
                .processedDate(getDatetime(row, "PROCESSED_DATE"))
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

    public Multi<Message> listAll(String tenantId) {
        return client.preparedQuery("SELECT ID, TENANT_ID, CREATED_BY, MESSAGE, SEND_TO, TYPE, STATUS, SCHEDULED_DATE, PROCESSED_DATE, CREATED_DATE, UPDATED_DATE FROM MESSAGE WHERE TENANT_ID = ? ")
                .execute(Tuple.of(tenantId))
                .toMulti()
                .flatMap(rs -> Multi.createFrom().iterable(rs))
                .map(this::of);
    }

    public Uni<Message> findByIdOptional(String tenantId, String id) {
        return client.preparedQuery("SELECT ID, TENANT_ID, CREATED_BY, MESSAGE, SEND_TO, TYPE, STATUS," +
                " SCHEDULED_DATE, PROCESSED_DATE, CREATED_DATE, UPDATED_DATE FROM MESSAGE" +
                " WHERE TENANT_ID = ? AND ID = ? ")
                .execute(Tuple.of(tenantId, id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(i -> i.hasNext() ? of(i.next()) : null);

    }

    public Uni<Message> persist(Message message) {
        return client.preparedQuery("INSERT INTO MESSAGE (ID, TENANT_ID, CREATED_BY, MESSAGE, SEND_TO, TYPE, STATUS," +
                " SCHEDULED_DATE, PROCESSED_DATE, CREATED_DATE, UPDATED_DATE) VALUES (?, ?, ?, ?, ?, ?, 'PENDING', NOW(), NULL, NOW(), NULL) ")
                .execute(Tuple.of(message.getId(), message.getTenant().getId(), message.getCreatedBy(), message.getMessage(),
                        message.getSendTo(), message.getType().toString()))
                .onItem().transform(i -> of(i.iterator().next()));
    }
}
