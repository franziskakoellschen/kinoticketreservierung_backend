package com.kinoticket.backend;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    
	/**
	 * Connects to a PostgreSQL Database given the DATABASE_URL is
	 * set as an environment variable.
	 * 		@return The PostgreSQL Database as DataSource
	 */
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
    
	/**
	 * Allows requests from 'Cross-Origin Resource Sharing' URLs.
	 * 		@return WebMvcConfigurer with allowed CORS origins.
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(
					"http://localhost:3000",
					"http://kinoticket-frontend-dev.herokuapp.com",
					"http://kinoticket-frontend-prod.herokuapp.com",
					"https://localhost:3000",
					"https://kinoticket-frontend-dev.herokuapp.com",
					"https://kinoticket-frontend-prod.herokuapp.com"
				);
			}
		};
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		
		mailSender.setUsername(System.getenv("KinoticketEmail"));
		mailSender.setPassword(System.getenv("KinoticketPassword"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		return mailSender;
	}
}
