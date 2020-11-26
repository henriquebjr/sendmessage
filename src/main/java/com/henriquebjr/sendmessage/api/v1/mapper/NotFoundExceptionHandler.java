package com.henriquebjr.sendmessage.api.v1.mapper;

import com.henriquebjr.sendmessage.service.exception.MessageNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<MessageNotFoundException> {

    @Override
    public Response toResponse(MessageNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(e)
                .build();
    }
}
