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

    public int test() {
        int x = 5;
        int y = 4;
        System.out.println(x+y);
        return x +y;
    }
}
