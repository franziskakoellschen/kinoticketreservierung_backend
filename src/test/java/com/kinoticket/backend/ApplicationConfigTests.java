package com.kinoticket.backend;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junitpioneer.jupiter.ClearEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class ApplicationConfigTests {

	@Autowired
	WebApplicationContext webApplicationContext;

	@Test
	void testDataSource() throws URISyntaxException {
		assertDoesNotThrow(new Executable() {
			@Override
			public void execute() throws Throwable {
				DataSource ds = new ApplicationConfig().dataSource();
				assertNotNull(ds);
			}
		});
	}

	@Test
	@ClearEnvironmentVariable(key = "DATABASE_URL")
	void testMissingDBUrl() throws Exception {

		assertDoesNotThrow(new Executable() {
			@Override
			public void execute() throws Throwable {
				DataSource ds = new ApplicationConfig().dataSource();
				assertNull(ds);
			}
		});
	}

	@Test
	@ClearEnvironmentVariable(key = "KINOTICKET_EMAIL")
	void testMissingMailConfig() {
		assertDoesNotThrow(new Executable() {
			@Override
			public void execute() throws Throwable {
				JavaMailSender ms = new ApplicationConfig().getJavaMailSender();
				assertNull(ms);
			}
		});
	}

	@Test
	void corsOriginsAllowed() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		// test forbidden origin
		mockMvc.perform(
				options("/test-cors")
						.header("Access-Control-Request-Method", "GET")
						.header("Origin", "http://www.someurl.com"))
				.andExpect(status().isForbidden());

		// test allowed origins
		String[] allowedOrigins = {
				"http://localhost:3000",
				"http://kinoticket-frontend-dev.herokuapp.com",
				"http://kinoticket-frontend-prod.herokuapp.com",
				"https://localhost:3000",
				"https://kinoticket-frontend-dev.herokuapp.com",
				"https://kinoticket-frontend-prod.herokuapp.com"
		};

		for (String origin : allowedOrigins) {
			mockMvc.perform(
					options("/test-cors")
							.header("Access-Control-Request-Method", "GET")
							.header("Origin", origin))
					.andExpect(status().isOk());
		}
	}
}
