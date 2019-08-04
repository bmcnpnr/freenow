package com.freenow.service.driver;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;


    public DefaultDriverService(final DriverRepository driverRepository)
    {
        this.driverRepository = driverRepository;
    }


    /**
     * Finds a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return created driver
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Transactional
    @Override public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
        if (driverDO.getCar() != null)
        {
            driverDO.getCar().setDriver(null);
            driverDO.setCar(null);
        }
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Transactional
    @Override public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Set and update the selected car for an online driver.
     *
     * @param driverId
     * @param carDO
     * @throws EntityNotFoundException when any of the given entities do not exist
     * @throws DataIntegrityViolationException when given driver cannot select the given car
     */
    @Transactional
    @Override public void selectCar(long driverId, CarDO carDO) throws EntityNotFoundException, DataIntegrityViolationException
    {
        DriverDO driverDO = findOnlineDriverChecked(driverId);
        driverDO.setCar(carDO);
    }


    /**
     * Deletes the selected car for an online driver.
     *
     * @param driverId
     * @param carDO
     * @throws EntityNotFoundException when any of the given driver does not exist
     * @throws DataIntegrityViolationException when given driver cannot deselect the given car
     */
    @Transactional
    @Override public void deselectCar(long driverId, CarDO carDO) throws EntityNotFoundException, DataIntegrityViolationException
    {
        DriverDO driverDO = findOnlineDriverChecked(driverId);
        if (driverDO.getCar() == null)
        {
            throw new EntityNotFoundException("This driver has no selected car!");
        }
        if (driverDO.getCar().equals(carDO))
        {
            driverDO.setCar(null);
        }
        else
        {
            LOG.error("This driver cannot deselect this car, because it belongs to a different driver!");
            throw new DataIntegrityViolationException("This driver cannot deselect this car!");
        }

    }


    /**
     * Update a driver's online status. If you want to make a driver online and it has a selected car, it will be automatically deselected.
     *
     * @param driverId
     * @param onlineStatus
     * @throws EntityNotFoundException when the given driver entity does not exist.
     */
    @Transactional
    @Override public void updateDriversOnlineStatus(long driverId, OnlineStatus onlineStatus) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        if (onlineStatus.equals(OnlineStatus.OFFLINE) && driverDO.getCar() != null)
        {
            driverDO.setCar(null);
        }
        driverDO.setOnlineStatus(onlineStatus);
    }


    /**
     * Searches drivers which matches the given parameters. Each parameter and car information's attributes are evaluated separately. If one of these parameters match,
     * the corresponding driver will be returned.
     *
     * @param onlineStatus
     * @param username
     * @param carInformation
     * @throws EntityNotFoundException when no drivers is found
     */
    @Override public Collection<DriverDO> searchDrivers(OnlineStatus onlineStatus, String username, CarDTO carInformation)
        throws EntityNotFoundException
    {
        String onlineStatusStr = null, engineTypeStr = null;
        if (onlineStatus != null)
        {
            onlineStatusStr = onlineStatus.toString();
        }
        if (carInformation != null && carInformation.getEngineType() != null)
        {
            engineTypeStr = carInformation.getEngineType().toString();
        }
        List<DriverDO> drivers = driverRepository
            .searchDriversWithCarAttributes(onlineStatusStr, username, carInformation != null ? carInformation.getLicensePlate() : null,
                carInformation != null ? carInformation.getSeatCount() : null, carInformation != null ? carInformation.getConvertible() : null,
                carInformation != null ? carInformation.getRating() : null, engineTypeStr, carInformation != null ? carInformation.getManufacturer() : null);
        if (drivers.isEmpty())
        {
            throw new EntityNotFoundException("No drivers match the given search parameters!");
        }
        return drivers;
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatusAndDeletedFalse(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findByIdAndDeletedFalse(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find a driver entity with id: " + driverId));
    }


    private DriverDO findOnlineDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findByIdAndOnlineStatusAndDeletedFalse(driverId, OnlineStatus.ONLINE)
            .orElseThrow(() -> new EntityNotFoundException("Could not find an online driver with id: " + driverId));
    }


    public DriverRepository getDriverRepository()
    {
        return driverRepository;
    }
}
