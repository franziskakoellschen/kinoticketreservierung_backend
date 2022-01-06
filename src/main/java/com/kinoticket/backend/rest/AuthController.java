package com.kinoticket.backend.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.kinoticket.backend.dto.*;
import com.kinoticket.backend.model.ERole;
import com.kinoticket.backend.model.Role;
import com.kinoticket.backend.model.User;
import com.kinoticket.backend.repositories.RoleRepository;
import com.kinoticket.backend.repositories.UserRepository;
import com.kinoticket.backend.security.JwtUtils;
import com.kinoticket.backend.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired AuthenticationManager authenticationManager;
    @Autowired RoleRepository roleRepository;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder encoder;
    @Autowired JwtUtils jwtUtils;

    @PostMapping("/isUserRegistered")
    public ResponseEntity<?> isUserRegistered(@RequestBody UsernameCheckRequest request) {
        
        if (userRepository.existsByUsername(request.getUsername()))
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails
            .getAuthorities()
            .stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            new JwtResponse(jwt,
                            userDetails.getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            roles
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user account
        User user = new User(signUpRequest.getUsername(),
                             signUpRequest.getEmail(),
                             encoder.encode(
                                signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            roles.add(getRoleFromERole(ERole.ROLE_USER));
        } else {
            roles.addAll(getRolesFromString(strRoles));
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private Set<Role> getRolesFromString(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    roles.add(getRoleFromERole(ERole.ROLE_ADMIN));
                    break;
                default:
                    roles.add(getRoleFromERole(ERole.ROLE_USER));
            }
        });
        return roles;
    }

    private Role getRoleFromERole(ERole role) {
        Optional<Role> oRole = roleRepository.findByName(role);
        if (oRole.isPresent()) {
            return oRole.get();
        } else {
            return roleRepository.save(new Role(role));
        }
    }
}
