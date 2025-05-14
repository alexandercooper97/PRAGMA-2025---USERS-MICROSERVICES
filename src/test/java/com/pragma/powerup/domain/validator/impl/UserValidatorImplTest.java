package com.pragma.powerup.domain.validator.impl;

import com.pragma.powerup.domain.exception.UserException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import com.pragma.powerup.domain.validator.UserValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserValidatorImplTest {

    public static final String EMAIL = "jaimetr97@gmail.com";
    @Mock
    private UserModelPersistence userModelPersistence;

    private UserValidator userValidator;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userValidator = new UserValidatorImpl(userModelPersistence);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void save() {
        UserModel userModel = new UserModel();
        userModel.setEmail(EMAIL);

        when(userModelPersistence.existsByEmail(anyString())).thenReturn(false);

        assertDoesNotThrow(() -> userValidator.save(userModel));
    }

    @Test
    @DisplayName("When exist user with email provided")
    void saveFail() {
        UserModel userModel = new UserModel();
        userModel.setEmail(EMAIL);

        when(userModelPersistence.existsByEmail(anyString())).thenReturn(true);

        assertThrows(UserException.class, () -> userValidator.save(userModel));
    }
}