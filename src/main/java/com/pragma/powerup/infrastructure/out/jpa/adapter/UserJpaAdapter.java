package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.NoUserFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements UserModelPersistence {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public UserModel saveUser(UserModel userModel) {
        UserEntity owner = userRepository.save(userEntityMapper.toEntity(userModel));
        return userEntityMapper.toUserModel(owner);
    }

    @Override
    public List<UserModel> getAllOwners() {
        List<UserEntity> ownerList = userRepository.findAll();
        if (ownerList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userEntityMapper.toUserModelList(ownerList);
    }

    @Override
    public UserModel getUser(Long idUser) {
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(NoUserFoundException::new);
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public UserModel findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(NoUserFoundException::new);
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
