package com.pragma.powerup.infrastructure.configuration.security;

import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    private CustomUserDetailsService customUserDetailsService;

    private AutoCloseable autoCloseable;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        customUserDetailsService = new CustomUserDetailsService();

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("OWNER");

        userEntity = new UserEntity();
        userEntity.setEmail("jaimetr97@gmail.com");
        userEntity.setPassword("1231456");
        userEntity.setRole(roleEntity);

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @Disabled
    void loadUserByUsername() {

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEntity.getEmail());

        assertEquals(userEntity.getEmail(), userDetails.getUsername());
    }
}