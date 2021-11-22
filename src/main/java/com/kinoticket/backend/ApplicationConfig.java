package com.kinoticket.backend;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    
    @Bean
    public DataSource dataSource() throws URISyntaxException {

        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            PGSimpleDataSource basicDataSource = new PGSimpleDataSource();
            basicDataSource.setUrl(dbUrl);
            basicDataSource.setUser(username);
            basicDataSource.setPassword(password);

            return basicDataSource;
        } catch(NullPointerException e) {
            logger.error("DATABASE_URL is null");
            return null;
        }
    }
}
