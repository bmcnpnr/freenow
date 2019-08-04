package com.freenow.controller;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DriverService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;

    private final CarService carService;


    @Autowired
    public DriverController(final DriverService driverService, final CarService carService)
    {
        this.driverService = driverService;
        this.carService = carService;
    }


    @GetMapping("/getDriver/{driverId}")
    @ApiOperation(value = "Returns a driver", authorizations = {
        @Authorization(value = "Bearer")})
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping("/searchDrivers")
    @ApiOperation(value = "Searches drivers with the given parameters", authorizations = {
        @Authorization(value = "Bearer")})
    public List<DriverDTO> searchDrivers(@RequestParam OnlineStatus onlineStatus, @RequestParam String username, @RequestBody CarDTO carInformation) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTOList(driverService.searchDrivers(onlineStatus, username, carInformation));
    }


    @PutMapping("/createDriver")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a driver. Only Admin users can perform.", authorizations = {
        @Authorization(value = "Bearer")})
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/deleteDriver/{driverId}")
    @ApiOperation(value = "Deletes a driver. Only admin user can perform this", authorizations = {
        @Authorization(value = "Bearer")})
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PostMapping("/updateDriverLocation/{driverId}")
    @ApiOperation(value = "Updates a driver's location", authorizations = {
        @Authorization(value = "Bearer")})
    public void updateDriverLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping("/findAllDrivers")
    @ApiOperation(value = "Finds all drivers and returns them to the caller", authorizations = {
        @Authorization(value = "Bearer")})
    public List<DriverDTO> findAllDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }


    @PostMapping("/updateDriverStatus/{driverId}")
    @ApiOperation(value = "Updates a driver's online status", authorizations = {
        @Authorization(value = "Bearer")})
    public void updateDriverStatus(@PathVariable long driverId, @RequestParam OnlineStatus onlineStatus) throws EntityNotFoundException
    {
        driverService.updateDriversOnlineStatus(driverId, onlineStatus);
    }


    @PostMapping("/select/{driverId}")
    @ApiOperation(value = "Allows an online driver to select a car", authorizations = {
        @Authorization(value = "Bearer")})
    public void select(@PathVariable long driverId, @RequestParam long carId)
        throws EntityNotFoundException, CarAlreadyInUseException
    {
        try
        {
            driverService.selectCar(driverId, carService.find(carId));
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CarAlreadyInUseException("Car with id: " + carId + " is already selected by a different driver!");
        }
    }


    @PostMapping("/deselect/{driverId}")
    @ApiOperation(value = "Allows an online driver to deselect a car", authorizations = {
        @Authorization(value = "Bearer")})
    public void deselect(@PathVariable long driverId, @RequestParam long carId)
        throws EntityNotFoundException, CarAlreadyInUseException
    {
        try
        {
            driverService.deselectCar(driverId, carService.find(carId));
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CarAlreadyInUseException("Car with id: " + carId + " is already selected by a different driver!");
        }
    }


    public DriverService getDriverService()
    {
        return driverService;
    }


    public CarService getCarService()
    {
        return carService;
    }
}
