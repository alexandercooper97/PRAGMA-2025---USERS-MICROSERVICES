package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class UserAuthRequestDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
