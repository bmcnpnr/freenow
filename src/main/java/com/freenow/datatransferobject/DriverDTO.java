package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.security.enums.AuthorityTypes;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    @JsonIgnore
    private GeoCoordinate coordinate;

    private Set<AuthorityTypes> roles = new HashSet<>();


    private DriverDTO()
    {
    }


    private DriverDTO(Long id, String username, String password, GeoCoordinate coordinate, Set<AuthorityTypes> roles)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
        this.roles = roles;
    }


    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }

    @JsonProperty
    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }


    public Set<AuthorityTypes> getRoles()
    {
        return roles;
    }


    public static class DriverDTOBuilder
    {
        private Long id;
        private String username;
        private String password;
        private GeoCoordinate coordinate;
        private Set<AuthorityTypes> roles;


        public DriverDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }


        public DriverDTOBuilder setAuthorityTypes(Set<AuthorityTypes> roles)
        {
            this.roles = roles;
            return this;
        }


        public DriverDTO createDriverDTO()
        {
            return new DriverDTO(id, username, password, coordinate, roles);
        }

    }
}
