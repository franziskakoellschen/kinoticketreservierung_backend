package com.kinoticket.backend.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import com.kinoticket.backend.dto.JwtResponse;
import com.kinoticket.backend.dto.LoginRequest;
import com.kinoticket.backend.dto.MessageResponse;
import com.kinoticket.backend.dto.SignupRequest;
import com.kinoticket.backend.dto.UsernameCheckRequest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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
            new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
            )
        );
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
        User user = new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(
                signUpRequest.getPassword()
            )
        );

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
            if (role.isPresent()) {
                roles.add(role.get());
            } else {
                roles.add(roleRepository.save(new Role(ERole.ROLE_USER)));
            }
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Optional<Role> oRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                        if (oRole.isPresent()) {
                            roles.add(oRole.get());
                        } else {
                            roles.add(roleRepository.save(new Role(ERole.ROLE_ADMIN)));
                        }
                        break;
                    default:
                        oRole = roleRepository.findByName(ERole.ROLE_USER);
                        if (oRole.isPresent()) {
                            roles.add(oRole.get());
                        } else {
                            roles.add(roleRepository.save(new Role(ERole.ROLE_USER)));
                        }
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
