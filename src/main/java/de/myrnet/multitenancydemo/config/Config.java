package de.myrnet.multitenancydemo.config;

import de.myrnet.multitenancydemo.MultitenantDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static de.myrnet.multitenancydemo.Tenant.TENANT_DE;
import static de.myrnet.multitenancydemo.Tenant.TENANT_EN;

@Configuration
public class Config {

    private DefaultDataSourceConfig defaultDataSourceConfig;
    private TenantDeDataSourceConfig tenantDeDataSourceConfig;
    private TenantEnDataSourceConfig tenantEnDataSourceConfig;

    @Autowired
    public Config(DefaultDataSourceConfig defaultDataSourceConfig,
                  TenantDeDataSourceConfig tenantDeDataSourceConfig,
                  TenantEnDataSourceConfig tenantEnDataSourceConfig) {
        this.defaultDataSourceConfig = defaultDataSourceConfig;
        this.tenantDeDataSourceConfig = tenantDeDataSourceConfig;
        this.tenantEnDataSourceConfig = tenantEnDataSourceConfig;
    }

    @Bean
    public DataSource dataSource() {

        Map<Object,Object> resolvedDataSources = new HashMap<>();

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(this.getClass().getClassLoader());

        resolvedDataSources.put(TENANT_DE, dataSourceBuilder
                .url(tenantDeDataSourceConfig.getUrl())
                .username(tenantDeDataSourceConfig.getUsername())
                .password(tenantDeDataSourceConfig.getPassword())
                .build());

        resolvedDataSources.put(TENANT_EN, dataSourceBuilder
                .url(tenantEnDataSourceConfig.getUrl())
                .username(tenantEnDataSourceConfig.getUsername())
                .password(tenantEnDataSourceConfig.getPassword())
                .build());


        MultitenantDataSource dataSource = new MultitenantDataSource();

        dataSource.setDefaultTargetDataSource(dataSourceBuilder
                .url(defaultDataSourceConfig.getUrl())
                .username(defaultDataSourceConfig.getUsername())
                .password(defaultDataSourceConfig.getPassword())
                .build());
        dataSource.setTargetDataSources(resolvedDataSources);

        // Call this to finalize the initialization of the data source.
        dataSource.afterPropertiesSet();

        return dataSource;
    }


}
