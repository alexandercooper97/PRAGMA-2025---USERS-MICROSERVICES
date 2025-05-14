package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "users", indexes = {@Index(columnList = "email")})
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max = 50)
    private String name;

    @NotNull
    @Length(max = 50)
    private String lastname;

    @NotNull
    @Positive
    private Integer identityDocument;

    @NotNull
    @Pattern(regexp = "^\\+?\\d{2,13}$")
    private String phoneNumber;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private RoleEntity role;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;


}
