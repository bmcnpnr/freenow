package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultCarServiceTests
{
    private DefaultCarService carService;


    @Before
    public void initTest()
    {
        carService = new DefaultCarService(mock(CarRepository.class));
    }


    //todo: test creation with filled id
    @Test
    public void findCarWithSuccessTest() throws EntityNotFoundException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        car.setLicensePlate("License plate");
        when(carService.getCarRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(car));

        CarDO carResult = carService.find(1L);
        assertNotNull(carResult);
        assertEquals(car.getId(), carResult.getId());
        assertEquals(car.getLicensePlate(), carResult.getLicensePlate());
    }


    @Test(expected = EntityNotFoundException.class)
    public void findCarWithFailureTest() throws EntityNotFoundException
    {
        when(carService.getCarRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        carService.find(1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void findCarWithNullCarIdTest() throws EntityNotFoundException
    {
        when(carService.getCarRepository().findByIdAndDeletedFalse(null)).thenReturn(Optional.empty());
        carService.find(null);
    }


    @Test
    public void createCarWithSuccess() throws ConstraintsViolationException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        when(carService.getCarRepository().save(car)).thenReturn(car);

        CarDO carResult = carService.create(car);
        assertNotNull(carResult);
        assertEquals(car.getId(), carResult.getId());
    }


    @Test(expected = ConstraintsViolationException.class)
    public void createCarWithFailure() throws ConstraintsViolationException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        when(carService.getCarRepository().save(car)).thenThrow(new DataIntegrityViolationException("another car exists"));

        carService.create(car);

    }


    @Test
    public void deleteCarSuccessfully() throws EntityNotFoundException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        doAnswer(e -> {
            Object arg0 = e.getArgument(0);
            assertEquals(arg0, car);
            return null;
        }).when(carService.getCarRepository()).delete(car);
        when(carService.getCarRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(car));
        carService.delete(1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void tryToDeleteANonexistentCar() throws EntityNotFoundException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        when(carService.getCarRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        carService.delete(1L);
    }

}
