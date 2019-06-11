package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pool;
import io.vavr.collection.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository  extends JpaRepository<Pool, String> {

    @Query(value="select DISTINCT t from Pool t left join fetch t.reservations where t.poolId = :poolId")
    public Pool getFullByIdWithReservation(@Param("poolId") String poolId);

    @Query(value="select DISTINCT t from Pool t left join fetch t.periodicReservations where t.poolId = :poolId")
    Pool getFullByIdWithPeriodicReservation(@Param("poolId") String poolId);

    @Query(value="select t.poolId from Pool t")
    public List<String> getAllPoolId();

    @Query(value="select DISTINCT t from Pool t left join fetch t.tools")
    java.util.List<Pool> getAllPoolsWithTools();

    Pool getByPoolId(String poolId);
}
