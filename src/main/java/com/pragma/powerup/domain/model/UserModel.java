package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String name;
    private String lastname;
    private Integer identityDocument;
    private String phoneNumber;
    private String email;
    private String password;
    private RoleModel role;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
}
