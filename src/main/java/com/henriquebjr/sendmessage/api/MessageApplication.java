package com.henriquebjr.sendmessage.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "SendMessage", version = "1.0", description = "Serviço para envio de mensagens"))
public class MessageApplication extends Application {
}
