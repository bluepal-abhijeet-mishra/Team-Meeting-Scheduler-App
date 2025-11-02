package com.scheduler.userservice.service;

import com.scheduler.userservice.dto.SignUpRequest;
import com.scheduler.userservice.exception.UserAlreadyExistException;
import com.scheduler.userservice.model.Role;
import com.scheduler.userservice.model.User;
import com.scheduler.userservice.repository.RoleRepository;
import com.scheduler.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private SignUpRequest signUpRequest;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testuser");
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setPassword("password");
    }

    @Test
    void whenRegisterUserWithExistingUsername_thenThrowUserAlreadyExistException() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));
        assertThrows(UserAlreadyExistException.class, () -> authService.registerUser(signUpRequest));
    }

    @Test
    void whenRegisterUserWithExistingEmail_thenThrowUserAlreadyExistException() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(new User()));
        assertThrows(UserAlreadyExistException.class, () -> authService.registerUser(signUpRequest));
    }

    @Test
    void whenRegisterUserWithNewUser_thenSaveUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        authService.registerUser(signUpRequest);
    }
}
