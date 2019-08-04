package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

import java.util.Collection;

public interface CarService
{
    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO carDO) throws ConstraintsViolationException;

    void delete(Long carId) throws EntityNotFoundException;

    Collection<CarDO> findAllCars();
}
