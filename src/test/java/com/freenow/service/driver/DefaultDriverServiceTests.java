package com.freenow.service.driver;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultDriverServiceTests
{
    private DefaultDriverService driverService;


    @Before
    public void initTest()
    {
        driverService = new DefaultDriverService(mock(DriverRepository.class));
    }


    @Test
    public void findDriverWithSuccessTest() throws EntityNotFoundException
    {
        DriverDO driver = new DriverDO("driver01", null, null);
        driver.setId(1L);
        when(driverService.getDriverRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(driver));

        DriverDO driverResult = driverService.find(1L);
        assertNotNull(driverResult);
        assertEquals(driver.getId(), driverResult.getId());
        assertEquals(driver.getUsername(), driverResult.getUsername());
    }


    @Test(expected = EntityNotFoundException.class)
    public void findDriverWithFailureTest() throws EntityNotFoundException
    {
        when(driverService.getDriverRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());

        driverService.find(1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void findDriverWithNullDriverIdTest() throws EntityNotFoundException
    {
        when(driverService.getDriverRepository().findByIdAndDeletedFalse(null)).thenReturn(Optional.empty());
        driverService.find((Long) null);
    }


    @Test
    public void createDriverWithSuccess() throws ConstraintsViolationException
    {
        DriverDO driver = new DriverDO("driver01", null, null);
        driver.setId(1L);
        when(driverService.getDriverRepository().save(driver)).thenReturn(driver);

        DriverDO driverResult = driverService.create(driver);
        assertNotNull(driver);
        assertEquals(driver.getId(), driverResult.getId());
        assertEquals(driver.getUsername(), driverResult.getUsername());
    }


    @Test(expected = ConstraintsViolationException.class)
    public void createDriverWithFailure() throws ConstraintsViolationException
    {
        DriverDO driver = new DriverDO("driver01", null, null);
        driver.setId(1L);

        when(driverService.getDriverRepository().save(driver)).thenThrow(new DataIntegrityViolationException("another driver exists"));

        driverService.create(driver);
    }


    @Test
    public void deleteDriverSuccessfully() throws EntityNotFoundException
    {
        DriverDO driver = new DriverDO("driver01", null, null);
        driver.setId(1L);
        doAnswer(e -> {
            Object arg0 = e.getArgument(0);
            assertEquals(arg0, driver);
            return null;
        }).when(driverService.getDriverRepository()).delete(driver);
        when(driverService.getDriverRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(driver));
        driverService.delete(1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void tryToDeleteANonexistentDriver() throws EntityNotFoundException
    {
        DriverDO driver = new DriverDO("driver01", null, null);
        driver.setId(1L);

        when(driverService.getDriverRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());

        driverService.delete(1L);

    }


    @Test
    public void updateLocationSuccessfullyTest() throws EntityNotFoundException
    {
        DriverDO driver = new DriverDO("driver01", null, null);
        driver.setId(1L);
        driver.setCoordinate(new GeoCoordinate(1, 1));

        when(driverService.getDriverRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(driver));

        driverService.updateLocation(1L, 1, 1);
    }


    @Test(expected = EntityNotFoundException.class)
    public void tryToUpdateTheLocationOfNonexistentDriver() throws EntityNotFoundException
    {
        when(driverService.getDriverRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());

        driverService.updateLocation(1L, 1, 1);

    }


    @Test
    public void selectACarNotInUseTest() throws EntityNotFoundException
    {
        DriverDO driver = new DriverDO("driver01", null, null);
        driver.setId(1L);
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        when(driverService.getDriverRepository().findByIdAndOnlineStatusAndDeletedFalse(1L, OnlineStatus.ONLINE)).thenReturn(Optional.of(driver));
        driverService.selectCar(1L, car);
    }


    @Test(expected = EntityNotFoundException.class)
    public void driverNotFoundWhileSelectingCarTest() throws EntityNotFoundException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        when(driverService.getDriverRepository().findByIdAndOnlineStatusAndDeletedFalse(1L, OnlineStatus.ONLINE)).thenReturn(Optional.empty());
        driverService.selectCar(1L, car);
    }


    @Test
    public void deselectACarNotInUseTest() throws EntityNotFoundException
    {
        DriverDO driver = new DriverDO("driver01", null, null);
        driver.setId(1L);
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        driver.setCar(car);
        when(driverService.getDriverRepository().findByIdAndOnlineStatusAndDeletedFalse(1L, OnlineStatus.ONLINE)).thenReturn(Optional.of(driver));
        driverService.deselectCar(1L, car);
    }


    @Test(expected = EntityNotFoundException.class)
    public void driverNotFoundWhileDeselectingCarTest() throws EntityNotFoundException
    {
        CarDO car = new CarDO(null, null, null, null, null, null);
        car.setId(1L);
        when(driverService.getDriverRepository().findByIdAndOnlineStatusAndDeletedFalse(1L, OnlineStatus.ONLINE)).thenReturn(Optional.empty());
        driverService.deselectCar(1L, car);
    }


    @Test(expected = EntityNotFoundException.class)
    public void tryToUpdateTheStatusOfNonexistentUser() throws EntityNotFoundException
    {
        when(driverService.getDriverRepository().findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        driverService.updateDriversOnlineStatus(1L, OnlineStatus.OFFLINE);
    }


    @Test(expected = EntityNotFoundException.class)
    public void searchDriversWithNoMatchTest() throws EntityNotFoundException
    {
        CarDTO carDTO = CarDTO.newBuilder().setEngineType(EngineType.BIO_DIESEL).createCarDTO();
        when(driverService.getDriverRepository()
            .searchDriversWithCarAttributes(OnlineStatus.ONLINE.toString(), "driver1", carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getConvertible(),
                carDTO.getRating(), carDTO.getEngineType().toString(), carDTO.getManufacturer())).thenReturn(Collections.emptyList());
        driverService.searchDrivers(OnlineStatus.ONLINE, "driver1", carDTO);
    }

}
