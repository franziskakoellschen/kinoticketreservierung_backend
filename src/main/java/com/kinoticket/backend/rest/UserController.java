package com.kinoticket.backend.rest;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.Exceptions.MissingParameterException;
import com.kinoticket.backend.model.BankDetails;
import com.kinoticket.backend.model.User;
import com.kinoticket.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping()
    public ResponseEntity<User> postUser(@RequestBody User user) {
        ResponseEntity<User> responseEntity = null;
        User sentUser = null;
        try {
            service.createUser(user);
            responseEntity = new ResponseEntity<User>(HttpStatus.OK);
        } catch ( MissingParameterException mp){
            log.error("Missing Parameter: " + mp.getMessage());
            responseEntity = new ResponseEntity<User>(sentUser,HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        ResponseEntity<User> responseEntity = null;
        User sentUser = null;
        try {
            sentUser = service.getUser(id);
            responseEntity = new ResponseEntity<User>(sentUser,HttpStatus.OK);
        } catch ( EntityNotFound nf){
            log.error("Invalid Parameter: " + nf.getMessage());
            responseEntity = new ResponseEntity<User>(sentUser,HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping("/getid/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        ResponseEntity<User> responseEntity = null;
        User sentUser = null;
        try {
            sentUser = service.getUser(email);
            responseEntity = new ResponseEntity<User>(sentUser,HttpStatus.OK);
        } catch ( EntityNotFound nf){
            log.error("Invalid Parameter: " + nf.getMessage());
            responseEntity = new ResponseEntity<User>(sentUser,HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PatchMapping("/{id}")
    public void updateMasterData(@RequestBody User user){
        service.updateMasterData(user);
    }

    @PostMapping("/{id}/bankdetails")
    public void postBankDetails(@RequestBody BankDetails bankDetails){
        service.addBankDetails(bankDetails);
    }

    @PatchMapping("/{id}/bankdetails")
    public void updateBankDetails(@RequestBody BankDetails bankDetails){
        service.updateBankDetails(bankDetails);
    }

    @GetMapping("/{id}/bankdetails")
    public BankDetails getBankDetails(@PathVariable long id) {
        return service.getBankDetails(id);
    }

}
