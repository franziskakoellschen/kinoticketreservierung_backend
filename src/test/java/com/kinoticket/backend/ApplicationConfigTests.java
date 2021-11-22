package com.kinoticket.backend;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junitpioneer.jupiter.ClearEnvironmentVariable;

public class ApplicationConfigTests {

    
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
}
