package com.devesta.curricula.controllers;

import com.devesta.curricula.config.DataSourceConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;

@RestController
@RequestMapping("/config")
@Profile("admin")
public class ConfigController {
    @Autowired
    private DataSourceConfig dataSourceConfig;

    @GetMapping
    public DataSourceDto getDataSourceConfig() {
        return new DataSourceDto(
                dataSourceConfig.getUrl(),
                dataSourceConfig.getUsername(),
                dataSourceConfig.getPassword(),
                dataSourceConfig.getDriverClassName()
        );
    }

    @Data
    public static class DataSourceDto{
        private String url;
        private String username;
        private String password;
        private String driverClassName;

        public DataSourceDto(String url, String username, String password,String driverClassName) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.driverClassName=driverClassName;
        }

    }
}
