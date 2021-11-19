package com.kinoticket.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    public TestController() {}

    @GetMapping("/testRequest")
    public String testRequest() {
        return "Hello from Backend!";
    }

    private void test() {
        return;
    }
}
