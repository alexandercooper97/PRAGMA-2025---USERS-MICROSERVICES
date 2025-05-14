package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserTextAdapter implements UserModelPersistence {
    @Override
    public UserModel saveUser(UserModel userModel) {
        return null;
    }

    @Override
    public List<UserModel> getAllOwners() {
        return List.of();
    }

    @Override
    public UserModel getUser(Long idUser) {
        return null;
    }

    @Override
    public UserModel findUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
