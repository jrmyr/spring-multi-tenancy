package de.myrnet.multitenancydemo;

public enum Tenant {

    TENANT_DE,
    TENANT_EN,
    ;

    public static Tenant getTenantFromString(String tenantString) {
        try {
            return Tenant.valueOf(tenantString.toUpperCase());
        } catch (Exception ex) {
            return null;
        }
    }

}
