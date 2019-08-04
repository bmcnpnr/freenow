package com.freenow.service.driver;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;
import java.util.List;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    void selectCar(long driverId, CarDO car) throws EntityNotFoundException, DataIntegrityViolationException;

    void deselectCar(long driverId, CarDO car) throws EntityNotFoundException, DataIntegrityViolationException;

    void updateDriversOnlineStatus(long driverId, OnlineStatus onlineStatus) throws EntityNotFoundException;

    Collection<DriverDO> searchDrivers(OnlineStatus onlineStatus, String username, CarDTO carInformation) throws EntityNotFoundException;
}
