package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PeriodicReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodicReservationRepository extends JpaRepository<PeriodicReservation, Long> {

    @Query(value = "select r from PeriodicReservation r where r.pool.id = :poolId")
    List<PeriodicReservation> findAllByPoolId(@Param("poolId") String id);
}
