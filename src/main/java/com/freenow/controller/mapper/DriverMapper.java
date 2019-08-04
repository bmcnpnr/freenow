package com.freenow.controller.mapper;

import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        if (driverDTO == null)
        {
            return null;
        }
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword(), driverDTO.getRoles());
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        if (driverDO == null)
        {
            return null;
        }
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setUsername(driverDO.getUsername())
            .setCoordinate(driverDO.getCoordinate())
            .setAuthorityTypes(driverDO.getRoles());

        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        if (drivers == null)
        {
            return Collections.emptyList();
        }
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }
}
