package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserAuthRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDTO;
import com.pragma.powerup.application.dto.response.TokenResponseDto;
import com.pragma.powerup.application.dto.response.UserSimpleResponseDto;
import com.pragma.powerup.application.handler.AuthHandler;
import com.pragma.powerup.application.handler.UserHandler;
import com.pragma.powerup.application.mapper.TokenResponseMapper;
import com.pragma.powerup.application.mapper.UserAuthRequestMapper;
import com.pragma.powerup.application.mapper.UserRequestMapper;
import com.pragma.powerup.application.mapper.UserResponseMapper;
import com.pragma.powerup.domain.api.AuthService;
import com.pragma.powerup.domain.api.UserService;
import com.pragma.powerup.domain.model.TokenModel;
import com.pragma.powerup.domain.model.UserAuthModel;
import com.pragma.powerup.domain.model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthHandlerImplTest {
    public static final String TOKEN = "TOKEN";
    public static final String NAME = "Jaime";
    @Mock
    private AuthService authService;
    @Mock
    private UserRequestMapper userRequestMapper;
    @Mock
    private TokenResponseMapper tokenResponseMapper;
    @Mock
    private UserAuthRequestMapper userAuthRequestMapper;
    private AuthHandler authHandler;
    private AutoCloseable autoCloseable;
    private TokenResponseDto tokenResponseDto;
    private UserAuthModel userAuthModel;
    private TokenModel tokenModel;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        authHandler = new AuthHandlerImpl(authService, tokenResponseMapper, userAuthRequestMapper);

        tokenModel = new TokenModel();
        tokenModel.setToken(TOKEN);

        tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setToken(TOKEN);

        userAuthModel = new UserAuthModel();
        userAuthModel.setEmail("jaimetr97@gmail.com");
        userAuthModel.setPassword("123456789");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void authUser() {
        when(userAuthRequestMapper.toUserAuthModel(any(UserAuthRequestDto.class))).thenReturn(userAuthModel);
        when(authService.authUser(any(UserAuthModel.class))).thenReturn(tokenModel);
        when(tokenResponseMapper.toTokenResponse(any(TokenModel.class))).thenReturn(tokenResponseDto);

        TokenResponseDto tokenResponse = authHandler.authUser(new UserAuthRequestDto());
        assertEquals(tokenResponseDto.getToken(), tokenResponse.getToken());
        verify(authService, times(1)).authUser(userAuthModel);
    }

    @Test
    void validateToken() {
        when(authService.validateToken(any(String.class))).thenReturn(tokenModel);
        when(tokenResponseMapper.toTokenResponse(any(TokenModel.class))).thenReturn(tokenResponseDto);

        TokenResponseDto tokenResponse = authHandler.validateToken(TOKEN);
        assertEquals(tokenResponseDto.getToken(), tokenResponse.getToken());
        verify(authService, times(1)).validateToken(TOKEN);
    }
}