package com.kinoticket.backend.rest;


import com.kinoticket.backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authorization")
public class AuthorizationController
{


    @GetMapping("/user")
    public String showUser()
    {
        return "Welcome User!!";
    }


    @GetMapping("{username}")
    public String checkIfUserExists(@PathVariable("username") String username)
    {
        return("Hallo user: " + username);
    }

    @PostMapping("/register/{username}")
    public String createUser(@PathVariable("username") String username)
    {
        return("Hallo user: " + username);
    }



    @DeleteMapping("{username}")
    public String deleteUser(@PathVariable("username") String username)
    {
        return("Tschüss user: " + username);
    }
}

