package com.freenow.domainobject;

import com.freenow.domainvalue.EngineType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_licensePlate", columnNames = {"licensePlate"})
)
public class CarDO extends BaseDO
{
    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(nullable = false)
    @NotNull(message = "A car has to have a license plate!")
    private String licensePlate;

    @Column(nullable = false)
    private Integer seatCount;

    @Column
    private Boolean convertible;

    @Column
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column
    private EngineType engineType;

    @Column
    private String manufacturer;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL,
        fetch = FetchType.LAZY, optional = false)
    private DriverDO driver;


    public CarDO()
    {
    }


    public CarDO(String licensePlate, Integer seatCount, Boolean convertible, Integer rating, EngineType engineType, String manufacturer)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
        this.deleted = false;

    }


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public Integer getRating()
    {
        return rating;
    }


    public void setRating(Integer rating)
    {
        this.rating = rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }


    public DriverDO getDriver()
    {
        return driver;
    }


    public void setDriver(DriverDO driver)
    {
        this.driver = driver;
    }
}
