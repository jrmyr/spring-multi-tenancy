package de.myrnet.multitenancydemo.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public abstract class AbstractDataSourceConfig {

    private String url;
    private String username;
    private String password;

}
