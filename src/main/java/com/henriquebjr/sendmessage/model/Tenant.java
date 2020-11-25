package com.henriquebjr.sendmessage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Tenant {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private Boolean active;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public Tenant() {
    }

    public Tenant(String id) {
        this.id = id;
    }

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
        private Tenant tenant;

        private Builder() {
            tenant = new Tenant();
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder id(String id) {
            tenant.setId(id);
            return this;
        }

        public Builder name(String name) {
            tenant.setName(name);
            return this;
        }

        public Builder active(Boolean active) {
            tenant.setActive(active);
            return this;
        }

        public Builder createdDate(Date createdDate) {
            tenant.setCreatedDate(createdDate);
            return this;
        }

        public Builder updatedDate(Date updatedDate) {
            tenant.setUpdatedDate(updatedDate);
            return this;
        }

        public Tenant build() {
            return tenant;
        }
    }
}
