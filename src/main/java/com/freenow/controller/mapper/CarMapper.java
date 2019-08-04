package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CarMapper
{
    public static CarDO makeCarDO(CarDTO carDTO)
    {
        if (carDTO == null)
        {
            return null;
        }

        return new CarDO(carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getConvertible(),
            carDTO.getRating(), carDTO.getEngineType(), carDTO.getManufacturer());
    }


    public static CarDTO makeCarDTO(CarDO carDO)
    {
        if (carDO == null)
        {
            return null;
        }
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
            .setId(carDO.getId())
            .setLicensePlate(carDO.getLicensePlate())
            .setSeatCount(carDO.getSeatCount())
            .setConvertible(carDO.getConvertible())
            .setRating(carDO.getRating())
            .setEngineType(carDO.getEngineType())
            .setManufacturer(carDO.getManufacturer());

        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars)
    {
        if (cars == null)
        {
            return Collections.emptyList();
        }
        return cars.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }
}
