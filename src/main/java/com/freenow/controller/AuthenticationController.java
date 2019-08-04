package com.freenow.controller;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthenticationController
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private DriverRepository driverRepository;


    @PostMapping("/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password)
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtTokenProvider.createToken(
                username,
                driverRepository.findByUsernameAndDeletedFalse(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        }
        catch (AuthenticationException e)
        {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }


    public AuthenticationManager getAuthenticationManager()
    {
        return authenticationManager;
    }


    public JwtTokenProvider getJwtTokenProvider()
    {
        return jwtTokenProvider;
    }


    public DriverRepository getDriverRepository()
    {
        return driverRepository;
    }
}
