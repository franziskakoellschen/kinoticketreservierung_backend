package com.kinoticket.backend;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;


@TestConfiguration
public class UnitTestConfiguration {
    @Bean
    @Primary
    public JavaMailSender getMockJavaMailSender(){
        return Mockito.mock(JavaMailSender.class);        
    }
}
