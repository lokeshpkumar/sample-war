package com.dataken.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/secured")
public class SecuredService {

    @GET
    public String sayHello() {
        return "Hello from secured service ...";
    }
}
