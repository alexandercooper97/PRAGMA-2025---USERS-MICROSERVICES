package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.UserAuthRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDTO;
import com.pragma.powerup.application.dto.response.TokenResponseDto;

public interface AuthHandler {
    TokenResponseDto authUser(UserAuthRequestDto userAuthRequestDto);

    TokenResponseDto validateToken(String token);
}
