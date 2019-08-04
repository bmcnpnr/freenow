package com.freenow.datatransferobject;

import com.freenow.domainvalue.EngineType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarDTOTests
{
    @Test
    public void buildACarTest()
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
            .setId(1L)
            .setSeatCount(5)
            .setConvertible(false)
            .setEngineType(EngineType.BIO_DIESEL)
            .setLicensePlate("License plate")
            .setManufacturer("Toyota")
            .setRating(1);
        CarDTO carDTO = carDTOBuilder.createCarDTO();

        assertNotNull(carDTO);
        assertEquals(Long.valueOf(1L), carDTO.getId());
        assertEquals(Integer.valueOf(5), carDTO.getSeatCount());
        assertEquals(false, carDTO.getConvertible());
        assertEquals(EngineType.BIO_DIESEL, carDTO.getEngineType());
        assertEquals("License plate", carDTO.getLicensePlate());
        assertEquals("Toyota", carDTO.getManufacturer());
        assertEquals(Integer.valueOf(1), carDTO.getRating());
    }


    @Test
    public void buildACarWithNullFieldsTest()
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
            .setSeatCount(5)
            .setEngineType(EngineType.BIO_DIESEL)
            .setLicensePlate("License plate")
            .setManufacturer("Toyota");
        CarDTO carDTO = carDTOBuilder.createCarDTO();

        assertNotNull(carDTO);
        assertNull(carDTO.getId());
        assertEquals(Integer.valueOf(5), carDTO.getSeatCount());
        assertNull(carDTO.getConvertible());
        assertEquals(EngineType.BIO_DIESEL, carDTO.getEngineType());
        assertEquals("License plate", carDTO.getLicensePlate());
        assertEquals("Toyota", carDTO.getManufacturer());
        assertNull(carDTO.getRating());
    }
}
