package com.mycompany.myapp.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Pool is disabled")
public class PoolDisabledException extends RuntimeException{
    private static final long serialVersionUID = 100L;
}
