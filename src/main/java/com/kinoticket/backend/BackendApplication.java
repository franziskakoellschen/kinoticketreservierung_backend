package com.kinoticket.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	/**
	 * Allows requests from 'Cross-Origin Resource Sharing' URLs.
	 * @return WebMvcConfigurer with allowed CORS origins.
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
}
