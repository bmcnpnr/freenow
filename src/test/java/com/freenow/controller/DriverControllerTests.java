package com.freenow.controller;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DriverService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverControllerTests
{
    private DriverController driverController;


    @Before
    public void initTest()
    {
        driverController = new DriverController(mock(DriverService.class), mock(CarService.class));
    }


    @Test
    public void getDriverSuccessTest() throws EntityNotFoundException
    {
        DriverDTO driverDTO = DriverDTO.newBuilder().setId(1L).setUsername("driver1").createDriverDTO();
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        when(driverController.getDriverService().find(1L)).thenReturn(driverDO);

        DriverDTO driver = driverController.getDriver(1L);
        assertNull(driver.getId());
        assertEquals(driverDTO.getUsername(), driver.getUsername());
    }


    @Test(expected = EntityNotFoundException.class)
    public void getDriverFailTest() throws EntityNotFoundException
    {
        when(driverController.getDriverService().find(23L)).thenThrow(new EntityNotFoundException("entity not found"));

        driverController.getDriver(23L);

    }


    @Test
    public void searchDriversWithMatchTest() throws EntityNotFoundException
    {
        when(driverController.getDriverService().searchDrivers(any(OnlineStatus.class), anyString(), any(CarDTO.class)))
            .thenReturn(new ArrayList<>());
        List<DriverDTO> drivers = driverController.searchDrivers(OnlineStatus.OFFLINE, "driver01", CarDTO.newBuilder().createCarDTO());
        assertNotNull(drivers);
        assertEquals(drivers.size(), 0);
    }


    @Test(expected = EntityNotFoundException.class)
    public void searchDriversWithErrorTest() throws EntityNotFoundException
    {
        when(driverController.getDriverService().searchDrivers(any(OnlineStatus.class), anyString(), any(CarDTO.class)))
            .thenThrow(new EntityNotFoundException("entity not found"));

        driverController.searchDrivers(OnlineStatus.OFFLINE, "driver21", CarDTO.newBuilder().createCarDTO());
    }


    @Test
    public void createDriverSuccessTest() throws ConstraintsViolationException
    {
        DriverDTO driverDTO = DriverDTO.newBuilder().setId(1L).setUsername("driver1").setPassword("driver1pw").createDriverDTO();
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        when(driverController.getDriverService().create(any(DriverDO.class))).thenReturn(driverDO);

        DriverDTO driver = driverController.createDriver(driverDTO);

        assertEquals(driver.getId(), driverDO.getId());
        assertEquals(driver.getUsername(), driverDO.getUsername());
        assertEquals(driver.getPassword(), driverDO.getPassword());
    }


    @Test(expected = ConstraintsViolationException.class)
    public void createDriverFailTest() throws ConstraintsViolationException
    {
        when(driverController.getDriverService().create(any(DriverDO.class))).thenThrow(new ConstraintsViolationException("collision in db"));

        driverController.createDriver(DriverDTO.newBuilder().createDriverDTO());
    }


    @Test
    public void deleteDriverSuccessTest() throws EntityNotFoundException
    {
        doAnswer((e) -> {
            Object arg0 = e.getArgument(0);
            assertEquals(arg0, 1L);
            return null;
        }).when(driverController.getDriverService()).delete(anyLong());

        driverController.deleteDriver(1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void deleteDriverFailTest() throws EntityNotFoundException
    {
        doThrow(new EntityNotFoundException("entity not found")).when(driverController.getDriverService()).delete(anyLong());

        driverController.deleteDriver(34L);
    }


    @Test
    public void updateDriverLocationWithSuccessTest() throws EntityNotFoundException
    {
        doAnswer((e) -> {
            Object arg0 = e.getArgument(0);
            Object arg1 = e.getArgument(1);
            Object arg2 = e.getArgument(2);
            assertEquals(arg0, 1L);
            assertEquals(arg1, 2.0);
            assertEquals(arg2, 3.0);
            return null;
        }).when(driverController.getDriverService()).updateLocation(1L, 2, 3);

        driverController.updateDriverLocation(1L, 2, 3);
    }


    @Test(expected = EntityNotFoundException.class)
    public void updateDriverLocationWithFailureTest() throws EntityNotFoundException
    {
        doThrow(new EntityNotFoundException("entity not found")).when(driverController.getDriverService()).updateLocation(1L, 2, 3);

        driverController.updateDriverLocation(1L, 2, 3);
    }


    @Test
    public void findAllOnlineDriversTest()
    {
        when(driverController.getDriverService().find(OnlineStatus.ONLINE)).thenReturn(new ArrayList<>());

        List<DriverDTO> drivers = driverController.findAllDrivers(OnlineStatus.ONLINE);

        assertNotNull(drivers);
        assertEquals(drivers.size(), 0);
    }


    @Test
    public void findAllOfflineDriversTest()
    {
        when(driverController.getDriverService().find(OnlineStatus.OFFLINE)).thenReturn(new ArrayList<>());

        List<DriverDTO> drivers = driverController.findAllDrivers(OnlineStatus.OFFLINE);

        assertNotNull(drivers);
        assertEquals(drivers.size(), 0);
    }


    @Test
    public void updateDriverStatusWithSuccessTest() throws EntityNotFoundException
    {
        doAnswer(e -> {
            Object arg0 = e.getArgument(0);
            Object arg1 = e.getArgument(1);
            Object arg2 = e.getArgument(2);
            assertEquals(arg0, 1L);
            assertEquals(arg1, 1.0);
            assertEquals(arg2, 2.0);
            return null;
        }).when(driverController.getDriverService()).updateLocation(1L, 1, 2);

        driverController.updateDriverLocation(1L, 1, 2);
    }


    @Test(expected = EntityNotFoundException.class)
    public void updateDriverStatusWithFailureTest() throws EntityNotFoundException
    {
        doThrow(new EntityNotFoundException("entity not found")).when(driverController.getDriverService()).updateLocation(1L, 1, 2);
        driverController.updateDriverLocation(1L, 1, 2);
    }


    @Test(expected = CarAlreadyInUseException.class)
    public void selectACarInUseTest() throws EntityNotFoundException, CarAlreadyInUseException
    {
        doThrow(new DataIntegrityViolationException("car in use")).when(driverController.getDriverService()).selectCar(anyLong(), any(CarDO.class));
        when(driverController.getCarService().find(1L)).thenReturn(new CarDO(null, null, null, null, null, null));
        driverController.select(1L, 1L);
    }


    @Test
    public void selectACarNotInUseTest() throws EntityNotFoundException, CarAlreadyInUseException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        doAnswer(e -> {
            Object arg0 = e.getArgument(0);
            Object arg1 = e.getArgument(1);
            assertEquals(arg0, 1L);
            assertEquals(arg1, car);
            return null;
        }).when(driverController.getDriverService()).selectCar(anyLong(), any(CarDO.class));

        driverController.select(1L, 1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void driverNotFoundWhileSelectingCarTest() throws EntityNotFoundException, CarAlreadyInUseException
    {
        doThrow(new EntityNotFoundException("driver cannot be found")).when(driverController.getDriverService()).selectCar(anyLong(), any(CarDO.class));

        when(driverController.getCarService().find(1L)).thenReturn(new CarDO(null, null, null, null, null, null));
        driverController.select(1L, 1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void carNotFoundWhileSelectingCarTest() throws CarAlreadyInUseException, EntityNotFoundException
    {
        doThrow(new EntityNotFoundException("car cannot be found")).when(driverController.getDriverService()).selectCar(anyLong(), any(CarDO.class));
        when(driverController.getCarService().find(1L)).thenReturn(new CarDO(null, null, null, null, null, null));
        driverController.select(1L, 1L);
    }


    @Test(expected = CarAlreadyInUseException.class)
    public void deselectACarInUseTest() throws EntityNotFoundException, CarAlreadyInUseException
    {
        doThrow(new DataIntegrityViolationException("car in use")).when(driverController.getDriverService()).deselectCar(anyLong(), any(CarDO.class));
        when(driverController.getCarService().find(1L)).thenReturn(new CarDO(null, null, null, null, null, null));
        driverController.deselect(1L, 1L);
    }


    @Test
    public void deselectACarNotInUseTest() throws CarAlreadyInUseException, EntityNotFoundException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        doAnswer(e -> {
            Object arg0 = e.getArgument(0);
            Object arg1 = e.getArgument(1);
            assertEquals(arg0, 1L);
            assertEquals(arg1, car);
            return null;
        }).when(driverController.getDriverService()).deselectCar(anyLong(), any(CarDO.class));

        when(driverController.getCarService().find(1L)).thenReturn(car);
        driverController.deselect(1L, 1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void driverNotFoundWhileDeselectingCarTest() throws EntityNotFoundException, CarAlreadyInUseException
    {
        doThrow(new EntityNotFoundException("driver cannot be found")).when(driverController.getDriverService()).deselectCar(anyLong(), any(CarDO.class));

        when(driverController.getCarService().find(1L)).thenReturn(new CarDO(null, null, null, null, null, null));
        driverController.deselect(1L, 1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void carNotFoundWhileDeselectingCarTest() throws EntityNotFoundException, CarAlreadyInUseException
    {
        doThrow(new EntityNotFoundException("car cannot be found")).when(driverController.getDriverService()).deselectCar(anyLong(), any(CarDO.class));

        when(driverController.getCarService().find(1L)).thenReturn(new CarDO(null, null, null, null, null, null));
        driverController.deselect(1L, 1L);
    }
}
