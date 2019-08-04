package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/cars")
public class CarController
{
    private final CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping("/getCar/{carId}")
    @ApiOperation(value = "Returns a car", authorizations = {
        @Authorization(value = "Bearer")})
    public CarDTO getCar(@PathVariable long carId) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(carId));
    }


    @PutMapping("/createCar")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a new car entity in the database. Only Admin users can perform.", authorizations = {
        @Authorization(value = "Bearer")})
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }


    @DeleteMapping("/deleteCar/{carId}")
    @ApiOperation(value = "Deletes a car. Only admin user can perform this", authorizations = {
        @Authorization(value = "Bearer")})
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
    }


    @GetMapping("/findAllCars")
    @ApiOperation(value = "Finds all cars and returns them to the caller", authorizations = {
        @Authorization(value = "Bearer")})
    public List<CarDTO> findAllCars()
    {
        return CarMapper.makeCarDTOList(carService.findAllCars());
    }


    public CarService getCarService()
    {
        return carService;
    }
}
