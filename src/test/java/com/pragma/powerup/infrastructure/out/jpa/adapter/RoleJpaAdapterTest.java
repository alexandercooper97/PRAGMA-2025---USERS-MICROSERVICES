package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.RoleModelPersistence;
import com.pragma.powerup.infrastructure.exception.NoRolFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.RoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.UserRepository;
import org.apache.http.auth.AUTH;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoleJpaAdapterTest {

    public static final long ID = 1L;
    public static final String NAME = "TEST";
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleEntityMapper roleEntityMapper;
    private RoleModelPersistence roleModelPersistence;
    private RoleModel roleModel;
    private RoleEntity roleEntity;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        roleModelPersistence = new RoleJpaAdapter(roleRepository, roleEntityMapper);
        roleModel = new RoleModel();
        roleModel.setId(ID);
        roleModel.setName(NAME);

        roleEntity= new RoleEntity();
        roleEntity.setId(ID);
        roleEntity.setName(NAME);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Find Role Successfully")
    void findById() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleEntity));
        when(roleEntityMapper.toRoleModel(any(RoleEntity.class))).thenReturn(roleModel);

        RoleModel roleModelResponse = roleModelPersistence.findById(ID);

        assertEquals(ID, roleModelResponse.getId());
    }

    @Test
    @DisplayName("No found Role by ID")
    void findByIdFail() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NoRolFoundException.class,  () -> roleModelPersistence.findById(ID));
    }

    @Test
    @DisplayName("Found Role Successfully by Name")
    void findByName() {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(roleEntity));
        when(roleEntityMapper.toRoleModel(any(RoleEntity.class))).thenReturn(roleModel);

        RoleModel roleModelResponse = roleModelPersistence.findByName(NAME);

        assertEquals(NAME, roleModelResponse.getName());
    }

    @Test
    @DisplayName("NO Found Role by Name")
    void findByNameFail() {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        assertThrows(NoRolFoundException.class,  () -> roleModelPersistence.findByName(NAME));

    }
}