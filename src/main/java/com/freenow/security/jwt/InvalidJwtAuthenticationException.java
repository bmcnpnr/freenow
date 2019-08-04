package com.freenow.security.jwt;

import org.springframework.security.core.AuthenticationException;

class InvalidJwtAuthenticationException extends AuthenticationException
{
    InvalidJwtAuthenticationException(String e)
    {
        super(e);
    }
}
