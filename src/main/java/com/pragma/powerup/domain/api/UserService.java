package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    List<UserModel> getAllOwners();
    void saveOwner(UserModel userModel);
    void saveClient(UserModel userModel);
    UserModel saveEmployee(UserModel userModel);

    UserModel getUser(Long idUser);

}
