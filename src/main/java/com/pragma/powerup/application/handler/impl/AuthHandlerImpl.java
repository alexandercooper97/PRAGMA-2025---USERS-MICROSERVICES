package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserAuthRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDTO;
import com.pragma.powerup.application.dto.response.TokenResponseDto;
import com.pragma.powerup.application.handler.AuthHandler;
import com.pragma.powerup.application.mapper.TokenResponseMapper;
import com.pragma.powerup.application.mapper.UserAuthRequestMapper;
import com.pragma.powerup.application.mapper.UserRequestMapper;
import com.pragma.powerup.domain.api.AuthService;
import com.pragma.powerup.domain.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandlerImpl implements AuthHandler {
    private final AuthService authService;
    private final TokenResponseMapper tokenResponseMapper;
    private final UserAuthRequestMapper userAuthRequestMapper;
    @Override
    public TokenResponseDto authUser(UserAuthRequestDto userAuthRequestDto) {
        return tokenResponseMapper.toTokenResponse(
                authService.authUser(
                        userAuthRequestMapper.toUserAuthModel(userAuthRequestDto)
                ));
    }

    @Override
    public TokenResponseDto validateToken(String token) {
        return tokenResponseMapper.toTokenResponse(authService.validateToken(token));
    }


}
