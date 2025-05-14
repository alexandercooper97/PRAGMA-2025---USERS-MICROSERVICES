package com.pragma.powerup.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoRolFoundException extends RuntimeException {
    public NoRolFoundException() {
        super("No Role Found");
    }
}
