package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.UserService;
import com.pragma.powerup.domain.model.RoleSystemModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.RoleModelPersistence;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import com.pragma.powerup.domain.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class UserUseCase implements UserService {

    private final UserModelPersistence userModelPersistence;
    private final RoleModelPersistence roleModelPersistence;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Override
    public List<UserModel> getAllOwners() {
        return userModelPersistence.getAllOwners();
    }

    @Override
    public UserModel getUser(Long idUser) {
        return userModelPersistence.getUser(idUser);
    }

    @Override
    public void saveOwner(UserModel userModel) {
        userValidator.save(userModel);
        userModel.setRole(roleModelPersistence.findByName(String.valueOf(RoleSystemModel.OWNER)));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModelPersistence.saveUser(userModel);
    }

    @Override
    public void saveClient(UserModel userModel) {
        userValidator.save(userModel);
        userModel.setRole(roleModelPersistence.findByName(String.valueOf(RoleSystemModel.CLIENT)));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModelPersistence.saveUser(userModel);
    }

    @Override
    public UserModel saveEmployee(UserModel userModel) {
        userValidator.save(userModel);
        userModel.setRole(roleModelPersistence.findByName(String.valueOf(RoleSystemModel.EMPLOYEE)));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userModelPersistence.saveUser(userModel);
    }
}
