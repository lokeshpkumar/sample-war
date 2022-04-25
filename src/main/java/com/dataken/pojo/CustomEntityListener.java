package com.dataken.pojo;

import com.dataken.JerseyConfig;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class CustomEntityListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    public void setTenantId(Object obj) {
        TenantAware tenantAwareObj = (TenantAware) obj;
        String tenantId = JerseyConfig.tenantContext.get();
        tenantAwareObj.setTenantId(tenantId);
    }

}
