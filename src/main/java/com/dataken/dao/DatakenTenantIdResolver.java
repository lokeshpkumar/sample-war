package com.dataken.dao;

import com.dataken.JerseyConfig;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatakenTenantIdResolver implements CurrentTenantIdentifierResolver {

    private static final Logger log = LoggerFactory.getLogger(DatakenTenantIdResolver.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        boolean global = JerseyConfig.global.get();
        log.info(">>>>>>>>>> Global is set to: {}", global);
        return global ? "GLOBAL" : "TENANT";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
