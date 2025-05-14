package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.AuthService;
import com.pragma.powerup.domain.exception.UserException;
import com.pragma.powerup.domain.model.TokenModel;
import com.pragma.powerup.domain.model.UserAuthModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import com.pragma.powerup.infrastructure.configuration.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class AuthUseCase implements AuthService {
    private final UserModelPersistence userModelPersistence;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    @Override
    public TokenModel authUser(UserAuthModel authModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authModel.getEmail(),
                        authModel.getPassword()));

        if(!authentication.isAuthenticated()){
            throw new UserException("Invalid authentication");
        }

        UserModel userModel = userModelPersistence.findUserByEmail(authModel.getEmail());
        return new TokenModel(jwtHelper.createToken(userModel));
    }

    @Override
    public TokenModel validateToken(String token) {
        jwtHelper.validate(token);
        userModelPersistence.findUserByEmail(jwtHelper.getEmailFromToken(token));
        return new TokenModel(token);
    }
}
