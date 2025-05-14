package com.pragma.powerup.application.dto.validator.Impl;

import com.pragma.powerup.application.dto.validator.PhoneNumberValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = "^\\+?\\d{2,13}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
