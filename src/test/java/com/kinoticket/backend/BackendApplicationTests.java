package com.kinoticket.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(UnitTestConfiguration.class)
class BackendApplicationTests {

	// Performs a Start of the Spring Application
	@Test
	void contextLoads() {}

	@Test
	void applicationStarts() {
		BackendApplication.main(new String[0]);
	}
}
