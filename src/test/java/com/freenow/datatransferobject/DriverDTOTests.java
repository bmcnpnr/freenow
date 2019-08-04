package com.freenow.datatransferobject;

import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.security.enums.AuthorityTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverDTOTests
{
    @Test
    public void buildADriverTest()
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(1L)
            .setCoordinate(new GeoCoordinate(1, 1))
            .setUsername("driver1")
            .setPassword("driver1pw")
            .setAuthorityTypes(new HashSet<>(Collections.singletonList(AuthorityTypes.USER)));

        DriverDTO driverDTO = driverDTOBuilder.createDriverDTO();
        assertEquals(new GeoCoordinate(1, 1), driverDTO.getCoordinate());
        assertEquals("driver1", driverDTO.getUsername());
        assertEquals("driver1pw", driverDTO.getPassword());
        assertEquals(new HashSet<>(Collections.singletonList(AuthorityTypes.USER)), driverDTO.getRoles());
    }


    @Test
    public void buildADriverWithNullFieldsTest()
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(1L)
            .setUsername("driver1")
            .setPassword("driver1pw");

        DriverDTO driverDTO = driverDTOBuilder.createDriverDTO();
        assertNull(driverDTO.getCoordinate());
        assertEquals("driver1", driverDTO.getUsername());
        assertEquals("driver1pw", driverDTO.getPassword());
        assertNull(driverDTO.getRoles());
    }
}
