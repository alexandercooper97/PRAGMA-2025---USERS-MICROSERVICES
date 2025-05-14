package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserRequestDTO;
import com.pragma.powerup.application.dto.response.UserResponseDTO;
import com.pragma.powerup.application.dto.response.UserSimpleResponseDto;
import com.pragma.powerup.application.handler.UserHandler;
import com.pragma.powerup.application.mapper.UserRequestMapper;
import com.pragma.powerup.application.mapper.UserResponseMapper;
import com.pragma.powerup.domain.api.UserService;
import com.pragma.powerup.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandlerImpl implements UserHandler {
    private final UserService userService;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @Override
    public List<UserResponseDTO> getAllOwners() {
        return userResponseMapper.toResponseList(userService.getAllOwners());
    }

    @Override
    public UserSimpleResponseDto getUser(Long idUser) {
        return userResponseMapper.toResponseSimple(userService.getUser(idUser));
    }

    @Override
    public void saveOwner(UserRequestDTO userRequestDTO) {
        userService.saveOwner(userRequestMapper.toUserModel(userRequestDTO));
    }

    @Override
    public void saveClient(UserRequestDTO userRequestDTO) {
        userService.saveClient(userRequestMapper.toUserModel(userRequestDTO));
    }

    @Override
    public UserSimpleResponseDto saveEmployee(UserRequestDTO userRequestDTO) {
        UserModel userModel = userRequestMapper.toUserModel(userRequestDTO);
        return userResponseMapper.toResponseSimple(userService.saveEmployee(userModel));
    }
}
