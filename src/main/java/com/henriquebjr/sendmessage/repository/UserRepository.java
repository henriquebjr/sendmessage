package com.henriquebjr.sendmessage.repository;


import com.henriquebjr.sendmessage.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserRepository implements PanacheRepositoryBase<User, String> {

}
