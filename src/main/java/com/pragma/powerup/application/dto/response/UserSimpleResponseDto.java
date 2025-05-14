package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSimpleResponseDto {
    private long id;
    private String name;
    private String lastname;
    private String role;
    private String phoneNumber;
}
