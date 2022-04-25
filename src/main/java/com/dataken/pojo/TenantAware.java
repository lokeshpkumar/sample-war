package com.dataken.pojo;

public interface TenantAware {

    public String getTenantId();

    public void setTenantId(String tenantId);

    public boolean isGlobal();

    public void setGlobal(boolean global);

}
