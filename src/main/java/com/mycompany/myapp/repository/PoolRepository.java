package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository  extends JpaRepository<Pool, String>, JpaSpecificationExecutor<Pool> {

    @Query(value="select DISTINCT t from Pool t left join fetch t.reservations where t.poolId = :poolId")
    Pool getFullByIdWithReservation(@Param("poolId") String poolId);

    Pool getByPoolId(String poolId);
}
