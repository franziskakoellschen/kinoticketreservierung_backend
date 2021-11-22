package com.kinoticket.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BackendApplicationTests {

	@Autowired
	WebApplicationContext webApplicationContext;

	
	// Performs a Start of the Spring Application
	@Test
	void contextLoads() {}

	@Test
	void applicationStarts() {
		BackendApplication.main(new String[0]);
	}

	@Test
	void corsOriginsAllowed() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		// test forbidden origin
		mockMvc.perform(
			options("/test-cors")
			.header("Access-Control-Request-Method", "GET")
			.header("Origin", "http://www.someurl.com")
		).andExpect(status().isForbidden());

		// test allowed origins
		String[] allowedOrigins = {
			"http://localhost:3000",
			"http://kinoticket-frontend-dev.herokuapp.com",
			"http://kinoticket-frontend-prod.herokuapp.com",
			"https://localhost:3000",
			"https://kinoticket-frontend-dev.herokuapp.com",
			"https://kinoticket-frontend-prod.herokuapp.com"
		};

		for(String origin : allowedOrigins) {
			mockMvc.perform(
				options("/test-cors")
				.header("Access-Control-Request-Method", "GET")
				.header("Origin", origin)
			).andExpect(status().isOk());
		}
	}
}
