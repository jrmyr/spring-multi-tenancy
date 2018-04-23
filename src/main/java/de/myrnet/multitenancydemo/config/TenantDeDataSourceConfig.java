package de.myrnet.multitenancydemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "spring.datasource.tenant-de")
public class TenantDeDataSourceConfig extends AbstractDataSourceConfig {

}
