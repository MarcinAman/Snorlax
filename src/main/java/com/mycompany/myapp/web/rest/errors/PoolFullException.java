package com.mycompany.myapp.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not enough free machines in the pool for this period")
public class PoolFullException extends RuntimeException{
    private static final long serialVersionUID = 101L;
}
