package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.UserRequestDTO;
import com.pragma.powerup.application.dto.response.UserResponseDTO;
import com.pragma.powerup.application.dto.response.UserSimpleResponseDto;

import java.util.List;

public interface UserHandler {

    List<UserResponseDTO> getAllOwners();

    UserSimpleResponseDto getUser(Long idUser);

    void saveOwner(UserRequestDTO userRequestDTO);

    void saveClient(UserRequestDTO userAuthRequestDto);

    UserSimpleResponseDto saveEmployee(UserRequestDTO userRequestDTO);
}
