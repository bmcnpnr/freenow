package com.freenow.dataaccessobject;

import com.freenow.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collection;
import java.util.Optional;

/**
 * Database Access Object for car table.
 * <p/>
 */
@RequestScope
public interface CarRepository extends CrudRepository<CarDO, Long>
{
    Optional<CarDO> findByIdAndDeletedFalse(Long carId);

    Collection<CarDO> findAllByDeletedFalse();
}
