package com.freenow.security;

import com.freenow.dataaccessobject.DriverRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomDriverDetailsService implements UserDetailsService
{
    private DriverRepository driverRepository;


    public CustomDriverDetailsService(DriverRepository driverRepository)
    {
        this.driverRepository = driverRepository;
    }


    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        return this.driverRepository.findByUsernameAndDeletedFalse(username)
            .orElseThrow(() -> new UsernameNotFoundException("Driver with username: " + username + " not found"));
    }


    public DriverRepository getDriverRepository()
    {
        return driverRepository;
    }
}
