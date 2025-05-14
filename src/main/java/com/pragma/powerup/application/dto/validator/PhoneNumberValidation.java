package com.pragma.powerup.application.dto.validator;

import com.pragma.powerup.application.dto.validator.Impl.PhoneNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target( { FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumberValidation {
    public String message() default "Invalid phone number: Can Start with + and have maximum size of 13";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
