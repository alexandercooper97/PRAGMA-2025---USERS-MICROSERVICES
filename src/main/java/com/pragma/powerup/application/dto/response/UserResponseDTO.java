package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String name;
    private String lastname;
    private Integer identityDocument;
    private String phoneNumber;
    private String email;
    private String role;
}
