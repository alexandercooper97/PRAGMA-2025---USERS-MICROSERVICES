package com.pragma.powerup.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.application.dto.request.UserAuthRequestDto;
import com.pragma.powerup.application.dto.response.TokenResponseDto;
import com.pragma.powerup.application.handler.AuthHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthRestController.class)
class AuthRestControllerTest {
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQ2xpZW50IiwiaWQiOjksInN1YiI6ImphaW1ldHIxMDBAZ21haWwuY29tIiwiaWF0IjoxNjgzODI5NTAyLCJleHAiOjE2ODM4MzMxMDJ9.8_78k-t7SrSsTAE5x-2t_yKBcgVnigoewqzSfv0hjNM";

    @MockBean
    private AuthHandler authHandler;

    @Autowired
    private MockMvc mockMvc;

    private UserAuthRequestDto userAuthRequestDto;
    private TokenResponseDto tokenResponseDto;

    @BeforeEach
    void setUp() {
        userAuthRequestDto = new UserAuthRequestDto();
        userAuthRequestDto.setEmail("jaimetr97@gmail.com");
        userAuthRequestDto.setPassword("123456789");


        tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setToken(TOKEN);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "user", password = "test123")
    void authUser() throws Exception {
        when(authHandler.authUser(any(UserAuthRequestDto.class))).thenReturn(tokenResponseDto);
        this.mockMvc.perform(
                        post("/api/v1/auth/login/")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userAuthRequestDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").value(tokenResponseDto.getToken()));
    }

    @Test
    @WithMockUser(username = "user", password = "test123")
    void validateToken() throws Exception {
        when(authHandler.validateToken(any(String.class))).thenReturn(tokenResponseDto);
        this.mockMvc.perform(
                        post("/api/v1/auth/validate?token="+TOKEN)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").value(tokenResponseDto.getToken()));
    }
}