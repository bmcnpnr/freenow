package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freenow.domainvalue.EngineType;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "A car has to have a license plate!")
    private String licensePlate;
    private Integer seatCount;
    private Boolean convertible;
    private Integer rating;
    private EngineType engineType;
    private String manufacturer;


    private CarDTO()
    {
    }


    private CarDTO(
        Long id, String licensePlate, Integer seatCount, Boolean convertible,
        Integer rating, EngineType engineType, String manufacturer)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }


    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public Integer getRating()
    {
        return rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }


    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private Integer seatCount;
        private Boolean convertible;
        private Integer rating;
        private EngineType engineType;
        private String manufacturer;


        public CarDTO.CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTO.CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTO.CarDTOBuilder setSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTO.CarDTOBuilder setConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTO.CarDTOBuilder setRating(Integer rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTO.CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTO.CarDTOBuilder setManufacturer(String manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }


        public CarDTO createCarDTO()
        {
            return new CarDTO(id, licensePlate, seatCount, convertible,
                rating, engineType, manufacturer);
        }

    }
}
