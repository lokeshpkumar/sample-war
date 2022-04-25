package com.dataken.pojo;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners(value = CustomEntityListener.class)
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name="tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenantId = :tenantId")
public class AbstractGenericEntity implements TenantAware, Serializable {


    private static final long serialVersionUID = 6613782984094733279L;

    private String tenantId;
    private boolean global;

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public boolean isGlobal() {
        return global;
    }

    @Override
    public void setGlobal(boolean global) {
        this.global = global;
    }
}
