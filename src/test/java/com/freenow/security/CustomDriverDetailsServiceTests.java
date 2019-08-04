package com.freenow.security;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomDriverDetailsServiceTests
{
    private CustomDriverDetailsService customDriverDetailsService;


    @Before
    public void initTest()
    {
        customDriverDetailsService = new CustomDriverDetailsService(mock(DriverRepository.class));
    }


    @Test
    public void loadWithUsernameSuccessTest()
    {
        DriverDTO driverDTO = DriverDTO.newBuilder().setUsername("driver01").createDriverDTO();

        when(customDriverDetailsService.getDriverRepository().findByUsernameAndDeletedFalse("driver01")).thenReturn(Optional.of(DriverMapper.makeDriverDO(driverDTO)));

        DriverDO driverResult = (DriverDO) customDriverDetailsService.loadUserByUsername("driver01");
        assertNotNull(driverResult);
        assertEquals("driver01", driverResult.getUsername());
    }


    @Test(expected = UsernameNotFoundException.class)
    public void loadWithUsernameFailTest()
    {
        when(customDriverDetailsService.getDriverRepository().findByUsernameAndDeletedFalse("driver01")).thenReturn(Optional.empty());

        customDriverDetailsService.loadUserByUsername("driver01");
    }
}
