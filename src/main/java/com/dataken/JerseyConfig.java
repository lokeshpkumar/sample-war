package com.dataken;

import com.dataken.services.MainService;
import com.dataken.services.SecuredService;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/tryouts")
public class JerseyConfig extends ResourceConfig {

    public static ThreadLocal<String> tenantContext = new ThreadLocal<>();
    public static ThreadLocal<Boolean> global = new ThreadLocal<>();

    public JerseyConfig() {
        register(MainService.class);
        register(SecuredService.class);
    }

}
