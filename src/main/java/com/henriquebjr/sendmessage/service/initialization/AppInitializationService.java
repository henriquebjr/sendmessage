package com.henriquebjr.sendmessage.service.initialization;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.service.TenantService;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class AppInitializationService {

    private static final Logger LOGGER = Logger.getLogger(AppInitializationService.class.getName());

    @Inject
    TenantService tenantService;

    void onStart(@Observes StartupEvent ev) throws Exception {
        var countTenants = tenantService.countAll();

        if(countTenants == 0) {
            LOGGER.info("## Creating default tenant and user admin. ###");

            Tenant tenant = Tenant.Builder.of()
                    .name("default")
                    .build();
            tenantService.insert(tenant);
        }

    }

}
