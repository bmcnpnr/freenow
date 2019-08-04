package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.EngineType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarMapperTests
{

    @Test
    public void carDomainObjectCreationTest()
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
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        assertNull(carDO.getId());
        assertEquals(carDTO.getSeatCount(), carDO.getSeatCount());
        assertEquals(carDTO.getConvertible(), carDO.getConvertible());
        assertEquals(carDTO.getEngineType(), carDO.getEngineType());
        assertEquals(carDTO.getLicensePlate(), carDO.getLicensePlate());
        assertEquals(carDTO.getManufacturer(), carDO.getManufacturer());
        assertEquals(carDTO.getRating(), carDO.getRating());
    }


    @Test
    public void carDomainObjectCreationTestFromNullObject()
    {
        CarDO carDO = CarMapper.makeCarDO(null);
        assertNull(carDO);
    }


    @Test
    public void carDomainObjectCreationTestFromObjectWithNullFields()
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
            .setId(1L)
            .setSeatCount(5)
            .setEngineType(EngineType.BIO_DIESEL)
            .setLicensePlate("License plate");

        CarDTO carDTO = carDTOBuilder.createCarDTO();
        CarDO carDO = CarMapper.makeCarDO(carDTO);

        assertNull(carDO.getId());
        assertEquals(carDTO.getSeatCount(), carDO.getSeatCount());
        assertNull(carDTO.getConvertible());
        assertEquals(carDTO.getEngineType(), carDO.getEngineType());
        assertEquals(carDTO.getLicensePlate(), carDO.getLicensePlate());
        assertNull(carDO.getManufacturer());
        assertNull(carDO.getRating());
    }


    @Test
    public void carDataTransferObjectCreationTest()
    {
        CarDO car = new CarDO("License Plate", 2, true, 2, EngineType.HYBRID, "BMW");
        car.setId(1L);

        CarDTO carDTO = CarMapper.makeCarDTO(car);
        assertEquals(car.getId(), carDTO.getId());
        assertEquals(car.getConvertible(), carDTO.getConvertible());
        assertEquals(car.getEngineType(), carDTO.getEngineType());
        assertEquals(car.getLicensePlate(), carDTO.getLicensePlate());
        assertEquals(car.getManufacturer(), carDTO.getManufacturer());
        assertEquals(car.getRating(), carDTO.getRating());
        assertEquals(car.getSeatCount(), carDTO.getSeatCount());
    }


    @Test
    public void carDataTransferObjectCreationTestFromNullObject()
    {
        CarDTO carDTO = CarMapper.makeCarDTO(null);
        assertNull(carDTO);
    }


    @Test
    public void carDataTransferObjectCreationTestFromObjectWithNullFields()
    {
        CarDO car = new CarDO("License Plate", 2, null, null, EngineType.HYBRID, null);
        car.setId(1L);

        CarDTO carDTO = CarMapper.makeCarDTO(car);
        assertEquals(car.getId(), carDTO.getId());
        assertNull(carDTO.getConvertible());
        assertEquals(car.getEngineType(), carDTO.getEngineType());
        assertEquals(car.getLicensePlate(), carDTO.getLicensePlate());
        assertNull(carDTO.getManufacturer());
        assertNull(carDTO.getRating());
        assertEquals(car.getSeatCount(), carDTO.getSeatCount());
    }


    @Test
    public void convertCarDOListToCarDTOListTest()
    {
        List<CarDO> cars = new ArrayList<>();
        CarDO car1 = new CarDO(null, null, null, null, null, null);
        CarDO car2 = new CarDO(null, null, null, null, null, null);
        CarDO car3 = new CarDO(null, null, null, null, null, null);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        List<CarDTO> carDTOS = CarMapper.makeCarDTOList(cars);
        assertNotNull(carDTOS);
        assertEquals(cars.size(), carDTOS.size());
    }


    @Test
    public void makeCarDOListFromNullObject()
    {
        List<CarDTO> carDTOS = CarMapper.makeCarDTOList(null);
        assertNotNull(carDTOS);
        assertEquals(0, carDTOS.size());
    }
}
