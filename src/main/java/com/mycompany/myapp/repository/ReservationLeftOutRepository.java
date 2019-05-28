package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ReservationLeftOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationLeftOutRepository extends JpaRepository<ReservationLeftOut, Long> {

    @Query(value="select r.day from ReservationLeftOut r where r.reservation.id = :reservationId")
    List<LocalDate> findLeftOutDatesByReservationId(@Param("reservationId") Long id);

}
