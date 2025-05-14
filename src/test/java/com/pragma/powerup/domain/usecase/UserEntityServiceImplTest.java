package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.UserService;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.RoleModelPersistence;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import com.pragma.powerup.domain.validator.UserValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserEntityServiceImplTest {

    public static final long ID = 1L;
    @Mock
    private UserModelPersistence userModelPersistence;
    @Mock
    private RoleModelPersistence roleModelPersistence;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserValidator userValidator;

    private UserService userService;
    private AutoCloseable autoCloseable;
    private UserModel userModel;
    @Captor
    private ArgumentCaptor<UserModel> userCaptor;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserUseCase(userModelPersistence, roleModelPersistence, passwordEncoder, userValidator);

        RoleModel roleModel = new RoleModel();
        roleModel.setId(ID);


        userModel = new UserModel();
        userModel.setName("Jaime");
        userModel.setLastname("Torres");
        userModel.setIdentityDocument(1122233);
        userModel.setPhoneNumber("+51123456");
        userModel.setEmail("jaimetr97@gmail.com");
        userModel.setRole(roleModel);
        userModel.setPassword("123456789");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }


    @Test
    void getAllOwners() {
        // Arrange
        List<UserModel> userModelList = Arrays.asList(new UserModel(), new UserModel());
        when(userModelPersistence.getAllOwners()).thenReturn(userModelList);
        
        //Act
        List<UserModel> userModelGotten = userService.getAllOwners();
        
        //Assert
        assertFalse(userModelGotten.isEmpty());
        assertEquals(userModelList.size(), userModelGotten.size());

    }

    @Test
    void getUser() {
        UserModel userModelResponse = userService.getUser(ID);
        verify(userModelPersistence, times(1)).getUser(ID);
    }

    @Test
    void saveOwner() {
        userModel.getRole().setName(RoleSystemModel.OWNER.toString());

        when(roleModelPersistence.findByName(any(String.class))).thenReturn(userModel.getRole());
        when(passwordEncoder.encode(any(String.class))).thenReturn(userModel.getPassword());

        userService.saveOwner(userModel);
        verify(userModelPersistence).saveUser(userCaptor.capture());

        UserModel userModelCapture = userCaptor.getValue();

        assertEquals(userModelCapture.getRole().getName(), RoleSystemModel.OWNER.toString());
    }

    @Test
    @DisplayName("Create Employee Successfully")
    void saveEmployee() {
        userModel.getRole().setName(RoleSystemModel.EMPLOYEE.toString());

        when(passwordEncoder.encode(any(String.class))).thenReturn(userModel.getPassword());
        when(roleModelPersistence.findByName(any(String.class))).thenReturn(userModel.getRole());
        when(userModelPersistence.saveUser(any(UserModel.class))).thenReturn(userModel);
        doNothing().when(userValidator).save(any(UserModel.class));

        UserModel userModelCreated =  userService.saveEmployee(userModel);

        assertEquals(userModelCreated.getEmail(), userModel.getEmail());
        assertEquals(userModelCreated.getName(), userModel.getName());
        assertEquals(userModelCreated.getPassword(), userModel.getPassword());
        assertEquals(userModelCreated.getRole().getName(), RoleSystemModel.EMPLOYEE.toString());
    }

    @Test
    void saveClient() {
        userModel.getRole().setName(RoleSystemModel.CLIENT.toString());

        when(roleModelPersistence.findByName(any(String.class))).thenReturn(userModel.getRole());
        when(passwordEncoder.encode(any(String.class))).thenReturn(userModel.getPassword());

        userService.saveClient(userModel);
        verify(userModelPersistence).saveUser(userCaptor.capture());

        UserModel userModelCapture = userCaptor.getValue();

        assertEquals(userModelCapture.getRole().getName(), RoleSystemModel.CLIENT.toString());
    }
}