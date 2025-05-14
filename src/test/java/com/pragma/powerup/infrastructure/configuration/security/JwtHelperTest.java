package com.pragma.powerup.infrastructure.configuration.security;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtHelperTest {
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQ2xpZW50IiwiaWQiOjksInN1YiI6ImphaW1ldHIxMDBAZ21haWwuY29tIiwiaWF0IjoxNjgzODI5NTAyLCJleHAiOjE2ODM4MzMxMDJ9.8_78k-t7SrSsTAE5x-2t_yKBcgVnigoewqzSfv0hjNM";

    public static final String TOKEN_FOR_100_YEARS = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiT3duZXIiLCJpZCI6Miwic3ViIjoiamFpbWV0cjk4QGdtYWlsLmNvbSIsImlhdCI6MTY4NDEzMTA2MCwiZXhwIjo1Mjg0MTMxMDYwfQ.h_drbOaJ4hURlB9yJiIISa4BvP1mqqdvdu2qotqrGOA";
    public static final String EMAIL = "jaimetr97@gmail.com";
    private UserModel userModel;
    private JwtHelper jwtHelper;
    @BeforeEach
    void setUp() {
        jwtHelper = new JwtHelper();

        RoleModel roleModel = new RoleModel();
        roleModel.setName("OWNER");

        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setEmail(EMAIL);
        userModel.setRole(roleModel);
    }

    @Test
    void createToken() {
        String token = jwtHelper.createToken(userModel);
        assertNotNull(token);
    }

    @Test
    void validate() {
        assertThrows(ExpiredJwtException.class, () -> jwtHelper.validate(TOKEN));
    }

    @Test
    void validateSuccessfully() {
        assertDoesNotThrow(() -> jwtHelper.validate(TOKEN_FOR_100_YEARS));
    }

    @Test
    void getEmailFromToken() {
        String emailResponse = jwtHelper.getEmailFromToken(TOKEN_FOR_100_YEARS);
        assertNotNull(emailResponse);
    }
}