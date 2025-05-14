package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.spi.RoleModelPersistence;
import com.pragma.powerup.infrastructure.exception.NoRolFoundException;
import com.pragma.powerup.infrastructure.out.jpa.mapper.RoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleJpaAdapter implements RoleModelPersistence {
    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;
    @Override
    public RoleModel findById(Long id) {
        return roleEntityMapper.toRoleModel(
                roleRepository.findById(id)
                        .orElseThrow(NoRolFoundException::new));
    }

    @Override
    public RoleModel findByName(String name) {
        return roleEntityMapper.toRoleModel(
                roleRepository.findByName(name)
                        .orElseThrow(NoRolFoundException::new));
    }
}
