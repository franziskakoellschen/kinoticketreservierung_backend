package com.kinoticket.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class SecurityTestController {

    @GetMapping("/eins")
    public String ichBinGesichert(){
        return ("eins");
    }

    @GetMapping("/zwei")
    public String getAdminView(){
        return ("eins");
    }
}
