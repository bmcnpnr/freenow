package com.freenow.domainobject;

import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.security.enums.AuthorityTypes;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
    name = "driver",
    uniqueConstraints = {@UniqueConstraint(name = "uc_username", columnNames = {"username"}), @UniqueConstraint(name = "uc_car", columnNames = {"car_id"})}
)
public class DriverDO extends BaseDO implements UserDetails
{
    @Column(nullable = false)
    @NotNull(message = "Username can not be null!")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Password can not be null!")
    private String password;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Embedded
    private GeoCoordinate coordinate;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCoordinateUpdated = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnlineStatus onlineStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private CarDO car;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<AuthorityTypes> roles = new HashSet<>();


    private DriverDO()
    {
    }


    public DriverDO(String username, String password, Set<AuthorityTypes> roles)
    {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.deleted = false;
        this.coordinate = null;
        this.dateCoordinateUpdated = null;
        this.onlineStatus = OnlineStatus.OFFLINE;
    }


    @Override public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.roles.stream().map(Enum::toString).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    @Override public boolean isAccountNonExpired()
    {
        return true;
    }


    @Override public boolean isAccountNonLocked()
    {
        return true;
    }


    @Override public boolean isCredentialsNonExpired()
    {
        return true;
    }


    @Override public boolean isEnabled()
    {
        return true;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public OnlineStatus getOnlineStatus()
    {
        return onlineStatus;
    }


    public void setOnlineStatus(OnlineStatus onlineStatus)
    {
        this.onlineStatus = onlineStatus;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }


    public void setCoordinate(GeoCoordinate coordinate)
    {
        this.coordinate = coordinate;
        this.dateCoordinateUpdated = ZonedDateTime.now();
    }


    public CarDO getCar()
    {
        return car;
    }


    public void setCar(CarDO car)
    {
        this.car = car;
    }


    public Set<AuthorityTypes> getRoles()
    {
        return roles;
    }
}
