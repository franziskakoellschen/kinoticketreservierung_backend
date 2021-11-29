package com.kinoticket.backend.service;

import com.kinoticket.backend.model.BankDetails;
import com.kinoticket.backend.model.User;
import com.kinoticket.backend.repositories.AddressRepository;
import com.kinoticket.backend.repositories.BankDetailRepository;
import com.kinoticket.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BankDetailRepository bankDetailRepository;

    public void createUser(User user){

    }

    public User getUser(long id){
        return null;
    }

    public long getUserId (String email){
        return 0;
    }

    public void updateUser(long id){

    }

    public void updateMasterData(User user){

    }

    public void addBankDetails(BankDetails bankDetails){

    }

    public void updateBankDetails(BankDetails bankDetails){

    }

    public BankDetails getBankDetails(long id){
        return null;
    }

}
