package com.freenow.controller;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.security.jwt.JwtTokenProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTests
{
    private AuthenticationController authenticationController;


    @Before
    public void initTest()
    {
        authenticationController = new AuthenticationController();
        ReflectionTestUtils.setField(authenticationController, "authenticationManager", mock(AuthenticationManager.class));
        ReflectionTestUtils.setField(authenticationController, "jwtTokenProvider", mock(JwtTokenProvider.class));
        ReflectionTestUtils.setField(authenticationController, "driverRepository", mock(DriverRepository.class));

    }


    @Test
    public void userAuthenticationSuccessTest()
    {
        when(authenticationController.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken("driver01", "driver01pw")))
            .thenReturn(new UsernamePasswordAuthenticationToken("driver01", "driver01pw"));

        when(authenticationController.getDriverRepository().findByUsernameAndDeletedFalse(anyString())).
            thenReturn(Optional.of(new DriverDO("driver01", "driver01pw", new HashSet<>())));

        when(authenticationController.getJwtTokenProvider().createToken(anyString(), anySet())).thenReturn("Created dummy token");

        ResponseEntity login = authenticationController.login("driver01", "driver01pw");
        Assert.assertEquals(login.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(((HashMap) login.getBody()).get("username"), "driver01");
        Assert.assertEquals(((HashMap) login.getBody()).get("token"), "Created dummy token");
    }


    @Test(expected = AuthenticationException.class)
    public void userAuthenticationFailTest()
    {
        when(authenticationController.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken("driver01", "driver01pw")))
            .thenThrow(new BadCredentialsException("Invalid username/password supplied"));

        authenticationController.login("driver01", "driver01pw");
    }
}
