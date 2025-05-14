package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.UserModel;

import java.util.List;

public interface UserModelPersistence {
    UserModel saveUser(UserModel userModel);

    List<UserModel> getAllOwners();

    UserModel getUser(Long idUser);

    UserModel findUserByEmail(String email);

    boolean existsByEmail(String email);
}
