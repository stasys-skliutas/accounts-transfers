package com.vzurauskas.accountstransfers;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private Properties properties;

    private AppConfig(Properties properties) {
        this.properties = properties;
    }

    public static AppConfig load(String resourceName) {
        try {
            Properties properties = new Properties();
            properties.load(App.class.getClassLoader().getResourceAsStream(resourceName));
            return new AppConfig(properties);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read app properties");
        }
    }

    public String datasourceDriverClass() {
        return (String) properties.get("datasource.driverClass");
    }

    public String datasourceJdbcUrl() {
        return (String) properties.get("datasource.jdbcUrl");
    }

    public String datasourceUser() {
        return (String) properties.get("datasource.user");
    }

    public String datasourcePassword() {
        return (String) properties.get("datasource.password");
    }
}
