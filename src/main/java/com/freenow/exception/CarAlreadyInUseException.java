package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This car is already in use by a different driver")
public class CarAlreadyInUseException extends Exception
{
    public CarAlreadyInUseException(String message)
    {
        super(message);
    }

}
