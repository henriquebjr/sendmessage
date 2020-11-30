package com.henriquebjr.sendmessage.api.v1.dto;

import java.util.Date;

public class TenantDTO {

    private String id;

    private String name;

    private Boolean active;

    private Date createdDate;

    private Date updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        private TenantDTO tenantDTO;

        private Builder() {
            tenantDTO = new TenantDTO();
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder id(String id) {
            tenantDTO.setId(id);
            return this;
        }

        public Builder name(String name) {
            tenantDTO.setName(name);
            return this;
        }

        public Builder active(Boolean active) {
            tenantDTO.setActive(active);
            return this;
        }

        public Builder createdDate(Date createdDate) {
            tenantDTO.setCreatedDate(createdDate);
            return this;
        }

        public Builder updatedDate(Date updatedDate) {
            tenantDTO.setUpdatedDate(updatedDate);
            return this;
        }

        public TenantDTO build() {
            return tenantDTO;
        }
    }
}
