package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository  extends JpaRepository<Pool, String> {

    @Query(value="select t from Pool t join fetch t.reservations where t.poolId = :poolId")
    public Pool getFullByIdWithReservation(@Param("poolId") String poolId);
}
