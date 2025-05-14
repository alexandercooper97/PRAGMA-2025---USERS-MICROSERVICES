package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.NoUserFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.RoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserEntityJpaAdapterTest {

    public static final String TEST = "TEST";
    public static final long ID = 1L;
    public static final String PASSWORD = "TEST123";
    public static final String EMAIL = "jaimetr97@gmail.com";
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserEntityMapper userEntityMapper;
    private UserJpaAdapter userJpaAdapter;
    private UserModel userModel;
    private UserEntity userEntity;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userJpaAdapter = new UserJpaAdapter(userRepository, userEntityMapper);
        userModel = new UserModel();
        userModel.setName(TEST);
        userModel.setPassword(PASSWORD);

        userEntity= new UserEntity();
        userEntity.setId(ID);
        userEntity.setEmail(EMAIL);
        userEntity.setName(TEST);
        userEntity.setPassword(PASSWORD);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    @DisplayName("Get Owners when Exists")
    void getAllOwner() {
        // given
        List<UserEntity> owners = Arrays.asList(new UserEntity(), new UserEntity());
        List<UserModel> userModelList = Arrays.asList(new UserModel(), new UserModel());

        // when
        when(userRepository.findAll()).thenReturn(owners);
        when(userEntityMapper.toUserModelList(anyList())).thenReturn(userModelList);

        List<UserModel> ownersFromService = userJpaAdapter.getAllOwners();

        // then
        assertEquals(ownersFromService.size(), owners.size());

    }

    @Test
    @DisplayName("Get Owners when not Exists")
    void getAllOwnerException() {
        // given
        List<UserEntity> owners = new ArrayList<>();
        List<UserModel> userModelList = new ArrayList<>();

        // when
        when(userRepository.findAll()).thenReturn(owners);
        when(userEntityMapper.toUserModelList(anyList())).thenReturn(userModelList);

        //then
        assertThrows(NoDataFoundException.class, () -> userJpaAdapter.getAllOwners() );

    }

    @Test
    void saveUser() {
        userModel.setId(ID);

        when(userEntityMapper.toEntity(any(UserModel.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userEntityMapper.toUserModel(any(UserEntity.class))).thenReturn(userModel);

        UserModel userModelSaved = userJpaAdapter.saveUser(userModel);

        assertEquals(ID, userModelSaved.getId());
        assertEquals(TEST, userModelSaved.getName());
    }

    @Test
    void existsByEmail(){
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

        boolean existsResponse = userJpaAdapter.existsByEmail(userModel.getEmail());
        assertFalse(existsResponse);
    }

    @Test
    @DisplayName("Get user successfully")
    void getUser() {
        userModel.setId(ID);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserModel(any(UserEntity.class))).thenReturn(userModel);

        UserModel userModelResponse = userJpaAdapter.getUser(ID);

        assertEquals(ID, userModelResponse.getId());
    }

    @Test
    @DisplayName("Not Found User")
    void getUserFail() {
        userModel.setId(ID);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(NoUserFoundException.class, () ->userJpaAdapter.getUser(ID));
    }

    @Test
    @DisplayName("Get user successfully with the email")
    void findUserByEmail() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserModel(any(UserEntity.class))).thenReturn(userModel);

        UserModel userModelResponse = userJpaAdapter.findUserByEmail(EMAIL);

        assertEquals(userModel.getEmail(), userModelResponse.getEmail());
    }

    @Test
    @DisplayName("Not Found User by Email provided")
    void findUserByEmailFail() {

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        assertThrows(NoUserFoundException.class, () ->userJpaAdapter.findUserByEmail(EMAIL));
    }
}