package com.kinoticket.backend.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.model.Address;
import com.kinoticket.backend.model.User;
import com.kinoticket.backend.repositories.UserRepository;
import com.kinoticket.backend.repositories.VerificationTokenRepository;
import com.kinoticket.backend.rest.request.LoginRequest;
import com.kinoticket.backend.rest.request.SignupRequest;
import com.kinoticket.backend.rest.request.UsernameCheckRequest;
import com.kinoticket.backend.security.VerificationToken;
import com.kinoticket.backend.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(UnitTestConfiguration.class)
@AutoConfigureMockMvc
public class AuthControllerTests {
    
    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    UserRepository userRepository;

    @MockBean
    VerificationTokenRepository verificationTokenRepository;

    @MockBean
    EmailService emailService;

    @MockBean
    AuthenticationManager authenticationManager;

    private JacksonTester<UsernameCheckRequest> jsonUNCR;
    private JacksonTester<SignupRequest> jsonSuR;
    private JacksonTester<LoginRequest> jsonLIR;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
            JacksonTester.initFields(this, new ObjectMapper());
            mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testIsUsernameRegistered() throws Exception {

        UsernameCheckRequest usernameCheckRequest = new UsernameCheckRequest();
        usernameCheckRequest.setUsername("presentUser");
        
        when(userRepository.existsByUsername("presentUser")).thenReturn(true);
        mvc.perform(
            post("/auth/isUserRegistered").contentType(MediaType.APPLICATION_JSON).content(
                jsonUNCR.write(usernameCheckRequest).getJson())
            )
            .andExpect(status().isOk()
        );

        usernameCheckRequest.setUsername("notPresentUser");
        when(userRepository.existsByUsername("notPresentUser")).thenReturn(false);
        mvc.perform(
            post("/auth/isUserRegistered").contentType(MediaType.APPLICATION_JSON).content(
                jsonUNCR.write(usernameCheckRequest).getJson())
            )
            .andExpect(status().isNotFound());
    }

    @Test
    public void testRegistrationConfifmation() throws Exception {

        when(verificationTokenRepository.findByToken("testToken")).thenReturn(null);
        String contentAsString = mvc.perform(
            get("/auth/registrationConfirm").param("token", "testToken"))
            .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString, "Invalid Token");


        VerificationToken expiredToken = new VerificationToken();
        expiredToken.setExpiryDate(new Date());
        when(verificationTokenRepository.findByToken("testToken")).thenReturn(expiredToken);
        contentAsString = mvc.perform(
            get("/auth/registrationConfirm").param("token", "testToken"))
            .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString, "Token expired");


        VerificationToken validToken = new VerificationToken();
        validToken.setExpiryDate(new Date(new Date().getTime() + 1000));
        validToken.setUser(new User());
        when(verificationTokenRepository.findByToken("testToken")).thenReturn(validToken);
        Mockito.doNothing().when(verificationTokenRepository).deleteById(any());
        contentAsString = mvc.perform(
            get("/auth/registrationConfirm").param("token", "testToken"))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString, "Registration complete");
    }

    @Test
    public void signUpTest() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("testMail");
        signupRequest.setUsername("testUser");
        signupRequest.setPassword("testPW");

        User mockUser = Mockito.mock(User.class);

        when(userRepository.save(any())).thenReturn(mockUser);
        when(mockUser.getAddress()).thenReturn(Mockito.mock(Address.class));
        doNothing().when(emailService).sendRegistrationEmail(any(), any());
        MvcResult result = mvc.perform(
            post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(
                jsonSuR.write(signupRequest).getJson())
            )
            .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("User registered successfully!"));


        when(userRepository.existsByUsername("testUser")).thenReturn(true);
        result = mvc.perform(
            post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(
                jsonSuR.write(signupRequest).getJson())
            )
            .andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Error: Username is already taken!"));

        User user = new User();
        Address address = new Address();
        address.setEmailAddress("testMail");
        user.setAddress(address);
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user);

        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(userRepository.findAll()).thenReturn(allUsers);
        result = mvc.perform(
            post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(
                jsonSuR.write(signupRequest).getJson())
            )
            .andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Error: Email is already in use!")); 
    }

    
    @Test
    public void testSignupWithRoles() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("testMail");
        signupRequest.setUsername("testUser");
        signupRequest.setPassword("testPW");
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        signupRequest.setRoles(roles);

        User mockUser = Mockito.mock(User.class);

        when(userRepository.save(any())).thenReturn(mockUser);
        when(mockUser.getAddress()).thenReturn(Mockito.mock(Address.class));
        doNothing().when(emailService).sendRegistrationEmail(any(), any());
        MvcResult result = mvc.perform(
            post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(
                jsonSuR.write(signupRequest).getJson())
            )
            .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("User registered successfully!"));
    }

    @Test
    public void testBadCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPW");

        when(authenticationManager.authenticate(any())).thenThrow(Mockito.mock(BadCredentialsException.class));
        mvc.perform(
            post("/auth/signin").contentType(MediaType.APPLICATION_JSON).content(
                jsonLIR.write(loginRequest).getJson())
            )
        .andExpect(status().isUnauthorized());
    }

    @Test
    public void testInactiveUser() throws Exception {
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPW");

        when(authenticationManager.authenticate(any())).thenThrow(Mockito.mock(DisabledException.class));
        mvc.perform(
            post("/auth/signin").contentType(MediaType.APPLICATION_JSON).content(
                jsonLIR.write(loginRequest).getJson())
            )
        .andExpect(status().isLocked());

    }

    @Test
    public void testSuccessfulLogin() throws IOException, Exception {
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPW");

        User user = new User();
        user.setUsername("testUser");
        user.setAddress(new Address());
        
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(user);
        mvc.perform(
            post("/auth/signin").contentType(MediaType.APPLICATION_JSON).content(
                jsonLIR.write(loginRequest).getJson())
            )
        .andExpect(status().isOk());
    }
}
