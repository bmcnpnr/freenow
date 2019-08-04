package com.freenow.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);


    @Override
    public void commence(
        HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException
    {
        LOGGER.debug("Jwt authentication failed:" + authException);

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt authentication failed");

    }

}
