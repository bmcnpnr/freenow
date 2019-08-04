package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarControllerTests
{
    private CarController carController;


    @Before
    public void initTest()
    {
        carController = new CarController(mock(CarService.class));
    }


    @Test
    public void getCarSuccessTest() throws EntityNotFoundException
    {
        when(carController.getCarService().find(anyLong())).
            thenReturn(new CarDO("License Plate", 2, false, 2, EngineType.BIO_DIESEL, "Mercedes"));

        CarDTO car = carController.getCar(1L);
        Assert.assertNull(car.getId());
        assertEquals(car.getLicensePlate(), "License Plate");
        assertEquals(car.getSeatCount(), Integer.valueOf(2));
        assertEquals(car.getConvertible(), false);
        assertEquals(car.getRating(), Integer.valueOf(2));
        assertEquals(car.getEngineType(), EngineType.BIO_DIESEL);
        assertEquals(car.getManufacturer(), "Mercedes");
    }


    @Test(expected = EntityNotFoundException.class)
    public void getCarFailTest() throws EntityNotFoundException
    {
        when(carController.getCarService().find(anyLong())).
            thenThrow(new EntityNotFoundException("Entity not found"));

        carController.getCar(1L);
    }


    @Test
    public void createCarSuccessTest() throws ConstraintsViolationException
    {

        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
            .setId(1L)
            .setSeatCount(5)
            .setEngineType(EngineType.BIO_DIESEL)
            .setLicensePlate("License plate");

        CarDTO carDTO = carDTOBuilder.createCarDTO();
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        when(carController.getCarService().create(any(CarDO.class))).thenReturn(carDO);

        CarDTO car = carController.createCar(carDTO);
        assertEquals(car.getId(), carDO.getId());
        assertEquals(car.getLicensePlate(), carDO.getLicensePlate());
        assertEquals(car.getEngineType(), carDO.getEngineType());
        assertEquals(car.getSeatCount(), carDO.getSeatCount());
    }


    @Test(expected = ConstraintsViolationException.class)
    public void createCarFailTest() throws ConstraintsViolationException
    {
        when(carController.getCarService().create(any(CarDO.class))).thenThrow(new ConstraintsViolationException("Collision in db"));

        carController.createCar(CarDTO.newBuilder().createCarDTO());

    }


    @Test
    public void deleteCarSuccessTest() throws EntityNotFoundException
    {
        doAnswer((e) -> {
            Object arg0 = e.getArgument(0);
            assertEquals(arg0, 1L);
            return null;
        }).when(carController.getCarService()).delete(anyLong());

        carController.deleteCar(1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void deleteCarFailTest() throws EntityNotFoundException
    {
        doThrow(new EntityNotFoundException("entity not found")).when(carController.getCarService()).delete(anyLong());

        carController.deleteCar(21L);
    }


    @Test
    public void findAllCarsTest()
    {
        List<CarDO> cars = new ArrayList<>();
        cars.add(new CarDO(null, null, null, null, null, null));
        cars.add(new CarDO(null, null, null, null, null, null));
        cars.add(new CarDO(null, null, null, null, null, null));
        when(carController.getCarService().findAllCars()).thenReturn(cars);
        List<CarDTO> allCars = carController.findAllCars();
        assertEquals(allCars.size(), 3);
    }
}
