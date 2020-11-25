package com.henriquebjr.sendmessage.service;

import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MigrationService {

    @Inject
    Flyway flyway;

    public void checkMigration() {
        System.out.println(flyway.info()
            .current().getVersion().toString());
    }
}
