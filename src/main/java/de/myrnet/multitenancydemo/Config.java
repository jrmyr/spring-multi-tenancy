package de.myrnet.multitenancydemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static de.myrnet.multitenancydemo.Tenant.TENANT_DE;
import static de.myrnet.multitenancydemo.Tenant.TENANT_EN;

@Configuration
public class Config {

    @Primary
    @Bean(name="defaultDsProps")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties getDefaultDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean(name="tenantDeDsProps")
    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "spring.datasource.tenant-de")
    public DataSourceProperties getTenantDeDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean(name="tenantEnDsProps")
    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "spring.datasource.tenant-en")
    public DataSourceProperties getTenantEnDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource (
            @Qualifier("defaultDsProps") DataSourceProperties defaultDsProps,
            @Qualifier("tenantDeDsProps") DataSourceProperties tenantDeDsProps,
            @Qualifier("tenantEnDsProps") DataSourceProperties tenantEnDsProps) {

        Map<Object,Object> resolvedDataSources = new HashMap<>();

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(this.getClass().getClassLoader());

        resolvedDataSources.put(TENANT_DE, dataSourceBuilder
                .url(tenantDeDsProps.getUrl())
                .username(tenantDeDsProps.getUsername())
                .password(tenantDeDsProps.getPassword())
                .build());

        resolvedDataSources.put(TENANT_EN, dataSourceBuilder
                .url(tenantEnDsProps.getUrl())
                .username(tenantEnDsProps.getUsername())
                .password(tenantEnDsProps.getPassword())
                .build());

        MultitenantDataSource dataSource = new MultitenantDataSource();

        dataSource.setDefaultTargetDataSource(getDefaultDatasource(defaultDsProps));
        dataSource.setTargetDataSources(resolvedDataSources);

        // Call this to finalize the initialization of the data source.
        dataSource.afterPropertiesSet();

        return dataSource;
    }

    private DataSource getDefaultDatasource(DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create(this.getClass().getClassLoader())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .build();
    }

}
