package com.pragma.powerup.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoUserFoundException extends RuntimeException{

    public NoUserFoundException() {super("No User Found");}
}
