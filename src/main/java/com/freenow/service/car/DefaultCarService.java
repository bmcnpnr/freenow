package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class DefaultCarService implements CarService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCarService.class);

    private final CarRepository carRepository;


    public DefaultCarService(final CarRepository carRepository)
    {
        this.carRepository = carRepository;
    }


    /**
     * Finds a car by id.
     *
     * @param carId
     * @return found car
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override public CarDO find(Long carId) throws EntityNotFoundException
    {
        return findCarChecked(carId);
    }


    /**
     * Creates a new car.
     *
     * @param carDO
     * @return created car
     * @throws ConstraintsViolationException if a car already exists with the given license plate, ... .
     */
    @Override public CarDO create(CarDO carDO) throws ConstraintsViolationException
    {
        CarDO car;
        try
        {
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOGGER.error("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }


    /**
     * Deletes an existing car by id.
     *
     * @param carId
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Transactional
    @Override public void delete(Long carId) throws EntityNotFoundException
    {
        CarDO carDO = findCarChecked(carId);
        carDO.setDeleted(true);
        if (carDO.getDriver() != null)
        {
            carDO.getDriver().setCar(null);
            carDO.setDriver(null);
        }
    }


    /**
     * Find all cars.
     *
     */
    @Override public Collection<CarDO> findAllCars()
    {
        return carRepository.findAllByDeletedFalse();
    }


    private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        return carRepository.findByIdAndDeletedFalse(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find a car entity with id: " + carId));
    }


    public CarRepository getCarRepository()
    {
        return carRepository;
    }
}
