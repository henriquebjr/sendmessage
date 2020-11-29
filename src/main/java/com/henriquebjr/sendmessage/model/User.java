package com.henriquebjr.sendmessage.model;

import javax.json.bind.annotation.JsonbTransient;
import java.util.Date;

public class User {

    private String id;

    private String username;

    private String password;

    private String name;

    private Boolean active;

    private Date createdDate;

    private Date updatedDate;

    private Tenant tenant;

    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonbTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static final class Builder {
        private User user;

        private Builder() {
            user = new User();
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder id(String id) {
            user.setId(id);
            return this;
        }

        public Builder username(String username) {
            user.setUsername(username);
            return this;
        }

        public Builder password(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder name(String name) {
            user.setName(name);
            return this;
        }

        public Builder active(Boolean active) {
            user.setActive(active);
            return this;
        }

        public Builder createdDate(Date createdDate) {
            user.setCreatedDate(createdDate);
            return this;
        }

        public Builder updatedDate(Date updatedDate) {
            user.setUpdatedDate(updatedDate);
            return this;
        }

        public Builder tenant(Tenant tenant) {
            user.setTenant(tenant);
            return this;
        }

        public Builder role(String role) {
            user.setRole(role);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
