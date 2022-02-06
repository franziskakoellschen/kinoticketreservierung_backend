package com.kinoticket.backend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.dto.UserDTO;
import com.kinoticket.backend.model.Address;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.User;
import com.kinoticket.backend.repositories.UserRepository;
import com.kinoticket.backend.repositories.VerificationTokenRepository;
import com.kinoticket.backend.rest.BookingControllerTests;
import com.kinoticket.backend.security.VerificationToken;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class UserServiceTests {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    VerificationTokenRepository verificationTokenRepository;

    @Test
    void testActivateUser() {
        User user = new User();
        user.setId(1234);
        user.setActive(false);
        String token = UUID.randomUUID().toString();

        Mockito.doNothing().when(verificationTokenRepository).deleteById(anyLong());
        userService.activateUser(new VerificationToken(user, token));

        assertTrue(user.isActive());
    }

    @Test
    void testGetBookings() {
        User mockedUser = new User();
        ArrayList<Booking> arrayList = new ArrayList<Booking>();
        arrayList.add(BookingControllerTests.createBooking());
        mockedUser.setBookings(arrayList);

        assertEquals(1, userService.getActiveBookings(mockedUser).size());
    }

    @Test
    void testCreateUser() {
        User mockedUser = new User();
        mockedUser.setBookings(new ArrayList<Booking>());
        mockedUser.setAddress(new Address());
        mockedUser.getAddress().setEmailAddress("testMail");

        when(userRepository.save(any())).thenReturn(mockedUser);
        User createUser = userService.createUser("test", "testMail", "testPassword");

        assertNotNull(createUser.getAddress());
        assertNotNull(createUser.getBookings());
        assertEquals(createUser.getAddress().getEmailAddress(), "testMail");
    }

    @Test
    void testCreateVerificationToken() {
        User user = new User();
        String token = UUID.randomUUID().toString();

        userService.createVerificationToken(user, token);
    }

    @Test
    void testExistsUserWithEmail() {
        List<User> users = new ArrayList<>();
        User user = new User();
        Address address = new Address();
        address.setEmailAddress("testMail");
        user.setAddress(address);
        users.add(user);


        when(userRepository.findAll()).thenReturn(users);
        assertTrue(userService.existsUserWithEmail("testMail"));

        
        when(userRepository.findAll()).thenReturn(users);     
        assertFalse(userService.existsUserWithEmail("anotherEmail")); 
    }

    @Test
    void testLoadUserByUsername() {
        assertThrows(UsernameNotFoundException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                userService.loadUserByUsername("notPresentUser");   
            }
            
        });

        assertDoesNotThrow(new Executable() {

            @Override
            public void execute() throws Throwable {
                when(userRepository.findByUsername("presentUser")).thenReturn(Optional.of(Mockito.mock(User.class)));
                userService.loadUserByUsername("presentUser");   
            }
            
        });
    }

    @Test
    void testUpdateUser() {
        UserDTO newUser = new UserDTO();
        newUser.setCity("TestCity");
        newUser.setLastName("TestLastName");


        User oldUser = new User();
        oldUser.setAddress(new Address());

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any())).thenReturn(mockedUser());
        User updatedUser = userService.updateUser("testUser", newUser);
        assertTrue(updatedUser.getAddress().getCity().equals("TestCity"));
        assertTrue(updatedUser.getAddress().getLastName().equals("TestLastName"));
    }

    private User mockedUser() {
        User user = new User();
        user.setAddress(new Address());
        user.getAddress().setCity("TestCity");
        user.getAddress().setLastName("TestLastName");
        return user;
    }

}
