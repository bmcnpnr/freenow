package com.freenow.controller.mapper;

import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.security.enums.AuthorityTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverMapperTests
{
    @Test
    public void driverDomainObjectCreationTest()
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(1L)
            .setUsername("driver1")
            .setPassword("driver1pw")
            .setAuthorityTypes(new HashSet<>(Collections.singletonList(AuthorityTypes.USER)));

        DriverDTO driverDTO = driverDTOBuilder.createDriverDTO();
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);

        assertNull(driverDO.getId());
        assertEquals(driverDTO.getRoles(), driverDO.getRoles());
        assertEquals(driverDTO.getUsername(), driverDO.getUsername());
        assertEquals(driverDTO.getPassword(), driverDO.getPassword());
    }


    @Test
    public void driverDomainObjectCreationTestFromNullObject()
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(null);
        assertNull(driverDO);
    }


    @Test
    public void driverDomainObjectCreationTestFromObjectWithNullFields()
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(1L)
            .setUsername("driver1")
            .setPassword("driver1pw");

        DriverDTO driverDTO = driverDTOBuilder.createDriverDTO();
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);

        assertNull(driverDO.getId());
        assertNull(driverDO.getRoles());
        assertNull(driverDO.getCoordinate());
        assertEquals(driverDTO.getUsername(), driverDO.getUsername());
        assertEquals(driverDTO.getPassword(), driverDO.getPassword());
    }


    @Test
    public void driverDataTransferObjectCreationTest()
    {
        DriverDO driver = new DriverDO("driver1", "driver1pw",
            new HashSet<>(Collections.singletonList(AuthorityTypes.USER)));
        driver.setId(1L);
        DriverDTO driverDTO = DriverMapper.makeDriverDTO(driver);

        assertEquals(driver.getId(), driverDTO.getId());
        assertEquals(driver.getRoles(), driverDTO.getRoles());
        assertEquals(driver.getCoordinate(), driverDTO.getCoordinate());
        assertEquals(driver.getUsername(), driverDTO.getUsername());
        assertEquals(driver.getPassword(), driverDTO.getPassword());
    }


    @Test
    public void driverDataTransferObjectCreationTestFromNullObject()
    {
        DriverDTO driverDTO = DriverMapper.makeDriverDTO(null);
        assertNull(driverDTO);
    }


    @Test
    public void driverDataTransferObjectCreationTestFromObjectWithNullFields()
    {
        DriverDO driver = new DriverDO("driver1", "driver1pw", null);
        DriverDTO driverDTO = DriverMapper.makeDriverDTO(driver);

        assertNull(driverDTO.getId());
        assertNull(driverDTO.getRoles());
        assertNull(driverDTO.getCoordinate());
        assertEquals(driver.getUsername(), driverDTO.getUsername());
        assertEquals(driver.getPassword(), driverDTO.getPassword());
    }


    @Test
    public void convertDriverDOListToDriverDTOListTest()
    {
        List<DriverDO> drivers = new ArrayList<>();
        DriverDO driver1 = new DriverDO("driver1", "driver1pw", null);
        DriverDO driver2 = new DriverDO("driver2", "driver2pw", null);
        DriverDO driver3 = new DriverDO("driver3", "driver3pw", null);
        drivers.add(driver1);
        drivers.add(driver2);
        drivers.add(driver3);
        List<DriverDTO> driverDTOS = DriverMapper.makeDriverDTOList(drivers);
        assertNotNull(driverDTOS);
        assertEquals(drivers.size(), driverDTOS.size());
    }


    @Test
    public void makeDriverDOListFromNullObject()
    {
        List<DriverDTO> driverDTOS = DriverMapper.makeDriverDTOList(null);
        assertNotNull(driverDTOS);
        assertEquals(0, driverDTOS.size());
    }
}
