package com.henriquebjr.sendmessage.model;

import java.util.Date;

public class Message {

    private String id;

    private Tenant tenant;

    private User createdBy;

    private String message;

    private String sendTo;

    private MessageTypeEnum type;

    private MessageStatusEnum status;

    private Date scheduledDate;

    private Date processedDate;

    private Date createdDate;

    private Date updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public void setType(MessageTypeEnum type) {
        this.type = type;
    }

    public MessageStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MessageStatusEnum status) {
        this.status = status;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }


    public static final class Builder {
        private Message message;

        private Builder() {
            message = new Message();
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder id(String id) {
            message.setId(id);
            return this;
        }

        public Builder message(String msg) {
            message.setMessage(msg);
            return this;
        }

        public Builder tenant(Tenant tenant) {
            message.setTenant(tenant);
            return this;
        }

        public Builder createdBy(User createdBy) {
            message.setCreatedBy(createdBy);
            return this;
        }

        public Builder sendTo(String sendTo) {
            message.setSendTo(sendTo);
            return this;
        }

        public Builder type(MessageTypeEnum type) {
            message.setType(type);
            return this;
        }

        public Builder status(MessageStatusEnum status) {
            message.setStatus(status);
            return this;
        }

        public Builder scheduledDate(Date scheduledDate) {
            message.setScheduledDate(scheduledDate);
            return this;
        }

        public Builder processedDate(Date processedDate) {
            message.setProcessedDate(processedDate);
            return this;
        }

        public Builder createdDate(Date createdDate) {
            message.setCreatedDate(createdDate);
            return this;
        }

        public Builder updatedDate(Date updatedDate) {
            message.setUpdatedDate(updatedDate);
            return this;
        }

        public Message build() {
            return message;
        }
    }
}
