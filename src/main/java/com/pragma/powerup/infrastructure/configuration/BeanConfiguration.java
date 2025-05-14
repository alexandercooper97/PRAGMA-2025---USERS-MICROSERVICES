package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.AuthService;
import com.pragma.powerup.domain.api.UserService;
import com.pragma.powerup.domain.spi.RoleModelPersistence;
import com.pragma.powerup.domain.spi.UserModelPersistence;
import com.pragma.powerup.domain.usecase.AuthUseCase;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.domain.validator.UserValidator;
import com.pragma.powerup.domain.validator.impl.UserValidatorImpl;
import com.pragma.powerup.infrastructure.configuration.security.JwtHelper;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserTextAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.RoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleEntityMapper roleEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Bean
    public RoleModelPersistence roleModelPersistence(){
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public UserModelPersistence userModelPersistence() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public UserModelPersistence userModelText() {
        return new UserTextAdapter();
    }

    @Bean
    public UserValidator userValidator() {
        return new UserValidatorImpl(userModelPersistence());
    }
    @Bean
    public UserService userService() {
        return new UserUseCase(userModelPersistence(), roleModelPersistence(),
                passwordEncoder, userValidator());
    }

    @Bean
    public UserService userServiceText() {
        return new UserUseCase(userModelText(), roleModelPersistence(),
                passwordEncoder, userValidator());
    }

    @Bean
    public AuthService authService() {
        return new AuthUseCase(userModelPersistence(), authenticationManager, jwtHelper);
    }
}