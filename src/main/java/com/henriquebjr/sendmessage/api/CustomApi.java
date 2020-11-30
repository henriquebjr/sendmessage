package com.henriquebjr.sendmessage.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        tags = {
                @Tag(name ="Tenant", description = "Tenants administration."),
                @Tag(name ="User", description = "Users administration."),
                @Tag(name ="Message", description = "Schedule sending message.")
        },
        info = @Info(
                title = "SendMessage",
                version = "1.0",
                description = "Service for sending messages")
        )
public class CustomApi extends Application {
}
