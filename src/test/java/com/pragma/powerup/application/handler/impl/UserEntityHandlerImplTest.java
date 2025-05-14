package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserRequestDTO;
import com.pragma.powerup.application.dto.response.UserResponseDTO;
import com.pragma.powerup.application.dto.response.UserSimpleResponseDto;
import com.pragma.powerup.application.handler.UserHandler;
import com.pragma.powerup.application.mapper.UserRequestMapper;
import com.pragma.powerup.application.mapper.UserResponseMapper;
import com.pragma.powerup.domain.api.UserService;
import com.pragma.powerup.domain.model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class UserEntityHandlerImplTest {

    public static final String NAME = "Jaime";
    public static final String LASTNAME = "Torres";
    public static final int IDENTITY_DOCUMENT = 1122233;
    public static final String PHONE_NUMBER = "+51123456";
    public static final String EMAIL = "jaimetr97@gmail.com";
    public static final String PASSWORD = "123456789";
    @Mock
    private UserService userService;
    @Mock
    private UserRequestMapper userRequestMapper;
    @Mock
    private UserResponseMapper userResponseMapper;
    private UserHandler userHandler;
    private AutoCloseable autoCloseable;

    private UserSimpleResponseDto userSimpleResponseDto;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userHandler = new UserHandlerImpl(userService, userRequestMapper, userResponseMapper);

        userSimpleResponseDto = new UserSimpleResponseDto();
        userSimpleResponseDto.setId(1L);
        userSimpleResponseDto.setName(NAME);
        userSimpleResponseDto.setLastname(LASTNAME);
        userSimpleResponseDto.setPhoneNumber(PHONE_NUMBER);

        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName(NAME);
        userModel.setLastname(LASTNAME);
        userModel.setIdentityDocument(IDENTITY_DOCUMENT);
        userModel.setPhoneNumber(PHONE_NUMBER);
        userModel.setEmail(EMAIL);
        userModel.setPassword(PASSWORD);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void getAllOwners() {
        List<UserModel> userModelList = Arrays.asList(new UserModel(), new UserModel());
        List<UserResponseDTO> userResponseDTOList = Arrays.asList(new UserResponseDTO(), new UserResponseDTO());

        when(userService.getAllOwners()).thenReturn(userModelList);
        when(userResponseMapper.toResponseList(anyList())).thenReturn(userResponseDTOList);

        List<UserResponseDTO> userResponseDTOListAnswer = userHandler.getAllOwners();

        assertFalse(userResponseDTOListAnswer.isEmpty());
        assertEquals(userResponseDTOList.size(), userResponseDTOListAnswer.size());
    }

    @Test
    void getUser() {
        when(userService.getUser(any(Long.class))).thenReturn(userModel);
        when(userResponseMapper.toResponseSimple(any(UserModel.class))).thenReturn(userSimpleResponseDto);

        UserSimpleResponseDto userSimpleResponse = userHandler.getUser(userModel.getId());

        assertEquals(NAME, userSimpleResponse.getName());
    }

    @Test
    void saveEmployee() {

        when(userRequestMapper.toUserModel(any(UserRequestDTO.class))).thenReturn(userModel);
        when(userService.saveEmployee(any(UserModel.class))).thenReturn(userModel);
        when(userResponseMapper.toResponseSimple(any(UserModel.class))).thenReturn(userSimpleResponseDto);

        UserSimpleResponseDto userResponse = userHandler.saveEmployee(new UserRequestDTO());

        assertEquals(userResponse.getId(), userSimpleResponseDto.getId());
        assertEquals(userResponse.getPhoneNumber(), userSimpleResponseDto.getPhoneNumber());

    }

    @Test
    void saveOwner() {
        when(userRequestMapper.toUserModel(any(UserRequestDTO.class))).thenReturn(userModel);

        userHandler.saveOwner(new UserRequestDTO());

        verify(userService,times(1)).saveOwner(userModel);
    }

    @Test
    void saveClient() {
        when(userRequestMapper.toUserModel(any(UserRequestDTO.class))).thenReturn(userModel);

        userHandler.saveClient(new UserRequestDTO());

        verify(userService,times(1)).saveClient(userModel);
    }
}