package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.BankDetails;
import com.kinoticket.backend.model.User;
import com.kinoticket.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping()
    public void postUser(@RequestBody User user) {
        service.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return service.getUser(id);
    }

    @GetMapping("/getid/{email}")
    public long getUserId(@PathVariable String email) {
        return service.getUserId(email);
    }

    @PatchMapping("/{id}")
    public void updateMasterData(@RequestBody User user){
        service.updateMasterData(user);
    }

    @PostMapping()
    public void postBankDetails(@RequestBody BankDetails bankDetails){
        service.addBankDetails(bankDetails);
    }

    @PatchMapping("{id}/bankdetails")
    public void updateBankDetails(@RequestBody BankDetails bankDetails){
        service.updateBankDetails(bankDetails);
    }

    @GetMapping("{id}/bankdetails")
    public BankDetails getBankDetails(@PathVariable long id) {
        return service.getBankDetails(id);
    }

}
