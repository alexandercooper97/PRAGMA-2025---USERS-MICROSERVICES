package com.pragma.powerup.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.application.dto.request.UserRequestDTO;
import com.pragma.powerup.application.dto.response.UserResponseDTO;
import com.pragma.powerup.application.dto.response.UserSimpleResponseDto;
import com.pragma.powerup.application.handler.UserHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserRestController.class)
class UserEntityRestControllerTest {

    public static final String NAME = "Jaime";
    public static final String LASTNAME = "Torres";
    public static final String PHONE_NUMBER = "+51123456";
    public static final String HEADER = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQ2xpZW50IiwiaWQiOjksInN1YiI6ImphaW1ldHIxMDBAZ21haWwuY29tIiwiaWF0IjoxNjgzODI5NTAyLCJleHAiOjE2ODM4MzMxMDJ9.8_78k-t7SrSsTAE5x-2t_yKBcgVnigoewqzSfv0hjNM";
    public static final long ID_USER = 1L;
    @MockBean
    private UserHandler userHandler;

    @Autowired
    private MockMvc mockMvc;

    private UserRequestDTO userRequestDTO;

    @BeforeEach
    void setUp() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName(NAME);
        userRequestDTO.setLastname(LASTNAME);
        userRequestDTO.setIdentityDocument(1122233);
        userRequestDTO.setPhoneNumber(PHONE_NUMBER);
        userRequestDTO.setEmail("jaimetr97@gmail.com");
        userRequestDTO.setPassword("123456789");
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
    void getAllUsers() throws Exception {

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        userResponseDTOList.add(new UserResponseDTO());

        when(userHandler.getAllOwners()).thenReturn(userResponseDTOList);

        this.mockMvc.perform(
                get("/api/v1/users/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").isNotEmpty());


        verify(userHandler, times(1)).getAllOwners();
    }

    @Test
    @WithMockUser(username = "user", password = "test123")
    void getUser() throws Exception {

        UserSimpleResponseDto userResponse = new UserSimpleResponseDto();
        userResponse.setName(NAME);

        when(userHandler.getUser(any(Long.class))).thenReturn(userResponse);

        this.mockMvc.perform(
                        get("/api/v1/users/1/")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isString());


        verify(userHandler, times(1)).getUser(1L);
    }

    @Test
    @WithMockUser(username = "user", password = "test123")
    void saveOwner() throws Exception {

        doNothing().when(userHandler).saveOwner(any(UserRequestDTO.class));

        this.mockMvc.perform(
                        post("/api/v1/users/owner/")
                                .with(csrf())
                                .header("Authorization", HEADER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userRequestDTO))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", password = "test123")
    void saveEmployee() throws Exception {

        UserSimpleResponseDto userSimpleResponseDto = new UserSimpleResponseDto();
        userSimpleResponseDto.setId(ID_USER);
        userSimpleResponseDto.setName(NAME);

        when(userHandler.saveEmployee(any(UserRequestDTO.class))).thenReturn(userSimpleResponseDto);

        this.mockMvc.perform(
                        post("/api/v1/users/employee/")
                                .with(csrf())
                                .header("Authorization", HEADER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userRequestDTO))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID_USER))
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @WithMockUser(username = "user", password = "test123")
    void saveClient() throws Exception {

        doNothing().when(userHandler).saveClient(any(UserRequestDTO.class));

        this.mockMvc.perform(
                        post("/api/v1/users/client/")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userRequestDTO))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}