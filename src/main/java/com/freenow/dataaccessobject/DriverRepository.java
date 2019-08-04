package com.freenow.dataaccessobject;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

/**
 * Database Access Object for driver table.
 * <p/>
 */
@RequestScope
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{
    List<DriverDO> findByOnlineStatusAndDeletedFalse(OnlineStatus onlineStatus);

    Optional<DriverDO> findByIdAndOnlineStatusAndDeletedFalse(long driverId, OnlineStatus onlineStatus);

    Optional<DriverDO> findByIdAndDeletedFalse(Long driverId);

    Optional<DriverDO> findByUsernameAndDeletedFalse(String username);

    @Query(value = "select * from DRIVER d LEFT JOIN CAR c on d.CAR_ID = c.ID "
        + "where (:onlineStatus IS NULL OR D.ONLINE_STATUS = :onlineStatus) OR (:username IS NULL OR d.USERNAME = :username) "
        + "OR (D.CAR_ID IS NOT NULL AND ((:licensePlate IS NOT NULL AND c.LICENSE_PLATE = :licensePlate) OR (:seatCount IS NOT NULL AND c.SEAT_COUNT = :seatCount) "
        + "OR (:convertible IS NOT NULL AND c.CONVERTIBLE = :convertible) OR (:rating IS NOT NULL AND c.RATING = :rating) "
        + "OR (:engineType IS NOT NULL AND c.ENGINE_TYPE = :engineType) OR (:manufacturer IS NOT NULL AND c.MANUFACTURER = :manufacturer)))", nativeQuery = true)
    List<DriverDO> searchDriversWithCarAttributes(
        @Param("onlineStatus") String onlineStatus, @Param("username") String username, @Param("licensePlate") String licensePlate,
        @Param("seatCount") Integer seatCount, @Param("convertible") Boolean convertible, @Param("rating") Integer rating, @Param("engineType") String engineType,
        @Param("manufacturer") String manufacturer);
}
