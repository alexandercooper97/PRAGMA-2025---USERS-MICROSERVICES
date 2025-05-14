package com.pragma.powerup.application.dto.request;
/*
########################################################################################
##                                                                                    ##
##                   HU 01  - CREAR PROPIETARIO                                       ##
##                                                                                    ##
########################################################################################
 */
import com.pragma.powerup.application.dto.validator.PhoneNumberValidation;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.*;


@Getter
@Setter
public class UserRequestDTO {
    @NotNull
    @Length(max = 50)
    private String name;

    @NotNull
    @Length(max = 50)
    private String lastname;

    @NotNull
    @Positive
    private Integer identityDocument;

    @NotBlank
    @PhoneNumberValidation
    private String phoneNumber;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(max = 100)
    private String password;
}
