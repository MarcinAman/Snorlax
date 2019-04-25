package com.mycompany.myapp.web.rest.reservation;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Reservation;
import com.mycompany.myapp.service.reservation.ReservationService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReservationResource {

    ReservationService reservationService;

    public ReservationResource(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reserve")
    @Timed
    public ResponseEntity<Void> reserve(@RequestParam(value = "poolId") String poolId, @RequestParam(value = "count") int count) throws Exception {
        reservationService.reserve(poolId, count);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "reservation.failed", poolId)).build();
    }

    @GetMapping("/reservation")
    @Timed
    public ResponseEntity<List<Reservation>> getReservationForPoolId(@RequestParam(value = "poolId") String poolId) throws Exception {
        return ResponseUtil.wrapOrNotFound(Optional.of(reservationService.getAllByPoolId(poolId)));
    }

    @DeleteMapping("/reservation/{id}")
    @Timed
    public ResponseEntity<Void> deleteById(@RequestParam(value = "id") Long id) throws Exception {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "reservation.deleted", id.toString())).build();
    }
}