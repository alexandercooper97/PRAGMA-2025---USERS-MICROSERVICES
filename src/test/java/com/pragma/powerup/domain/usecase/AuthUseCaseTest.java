package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.AuthService;
import com.pragma.powerup.domain.api.UserService;
import com.pragma.powerup.domain.exception.UserException;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.TokenModel;
import com.pragma.powerup.domain.model.UserAuthModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.RoleModelPersistence;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import com.pragma.powerup.infrastructure.configuration.security.JwtHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AuthUseCaseTest {
    public static final String EMAIL = "test@gmail.com";
    public static final String CREDENTIALS = "12345678";
    public static final String TOKEN = "TOKEN";
    @Mock
    private UserModelPersistence userModelPersistence;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtHelper jwtHelper;

    private AuthService authService;
    private AutoCloseable autoCloseable;
    private UserModel userModel;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        authService = new AuthUseCase(userModelPersistence, authenticationManager, jwtHelper);

        userModel = new UserModel();
        userModel.setName("Jaime");
        userModel.setLastname("Torres");
        userModel.setIdentityDocument(1122233);
        userModel.setPhoneNumber("+51123456");
        userModel.setEmail("jaimetr97@gmail.com");
        userModel.setPassword("123456789");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }


    @Test
    @DisplayName("Auth Successfully")
    void authUser() {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, CREDENTIALS,
                List.of(new SimpleGrantedAuthority("TEST")));

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticationToken);
        when(userModelPersistence.findUserByEmail(any(String.class))).thenReturn(userModel);
        when(jwtHelper.createToken(any(UserModel.class))).thenReturn(TOKEN);

        TokenModel tokenModel = authService.authUser(new UserAuthModel(EMAIL, CREDENTIALS));

        assertEquals(TOKEN, tokenModel.getToken());
    }

    @Test
    @DisplayName("Auth Failed, no authenticated")
    void authUserFailed() {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, CREDENTIALS);
        UserAuthModel userAuthModel = new UserAuthModel(EMAIL, CREDENTIALS);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticationToken);

        assertThrows(UserException.class, () -> authService.authUser(userAuthModel));
    }

    @Test
    void validateToken() {
        doNothing().when(jwtHelper).validate(any(String.class));
        when(userModelPersistence.findUserByEmail(any(String.class))).thenReturn(new UserModel());
        when(jwtHelper.getEmailFromToken(any(String.class))).thenReturn(EMAIL);

        TokenModel tokenModelResponse = authService.validateToken(TOKEN);

        assertEquals(TOKEN, tokenModelResponse.getToken());
    }
}