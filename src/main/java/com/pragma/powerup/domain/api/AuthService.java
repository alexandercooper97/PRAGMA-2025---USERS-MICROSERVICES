package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.TokenModel;
import com.pragma.powerup.domain.model.UserAuthModel;

public interface AuthService {
    TokenModel authUser(UserAuthModel authModel);

    TokenModel validateToken(String token);
}
