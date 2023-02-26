package com.acme.notificationapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Profile(Profiles.LOCAL)
@Configuration
@EnableTransactionManagement
public class LocalConf {

    @Bean(name = "datasource")
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setPassword("postgres");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/notifications");
        return driverManagerDataSource;
    }
}
