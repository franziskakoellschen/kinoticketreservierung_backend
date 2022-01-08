package com.kinoticket.backend.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import com.kinoticket.backend.dto.UserDTO;
import com.kinoticket.backend.model.Address;
import com.kinoticket.backend.model.User;
import com.kinoticket.backend.repositories.AddressRepository;
import com.kinoticket.backend.repositories.UserRepository;
import com.kinoticket.backend.repositories.VerificationTokenRepository;
import com.kinoticket.backend.security.VerificationToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return user;
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(user, token);
        verificationTokenRepository.save(myToken);
    }

    @Transactional
    public void activateUser(VerificationToken token) {
        User user = token.getUser();
        user.setActive(true);
        verificationTokenRepository.deleteById(token.getId());
    }

    public User createUser(String username, String email, String password) {
        User newUser = new User(username, email, password);

        Address address = new Address();
        address.setEmailAddress(email);
        addressRepository.save(address);        
        newUser.setAddress(address);

        newUser.setBookings(new ArrayList<>());

        return userRepository.save(newUser);
    }

    public User updateUser(String username, UserDTO newUser) {
        User user = userRepository.findByUsername(username).get();
        
        /*
            For now, we don't allow update of
            * username
            * email
            * password
        */

        Address address = user.getAddress();
        address.setSurName(newUser.getSurName());
        address.setLastName(newUser.getLastName());
        address.setStreet(newUser.getStreet());
        address.setHouseNumber(newUser.getHouseNumber());
        address.setPostCode(newUser.getPostCode());
        address.setCity(newUser.getCity());
        address.setPhoneNumber(newUser.getPhoneNumber());

        addressRepository.save(address);
        return userRepository.save(user);
    }
}
