package com.pragma.powerup.domain.validator.impl;

import com.pragma.powerup.domain.exception.UserException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import com.pragma.powerup.domain.validator.UserValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {

    private final UserModelPersistence userModelPersistence;
    @Override
    public void save(UserModel userModel) {
        if(userModelPersistence.existsByEmail(userModel.getEmail())) {
            throw new UserException("Exists user with email " + userModel.getEmail());
        }
    }
}
