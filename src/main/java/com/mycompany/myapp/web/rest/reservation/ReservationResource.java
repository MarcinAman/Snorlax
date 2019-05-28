package com.mycompany.myapp.web.rest.reservation;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Reservation;
import com.mycompany.myapp.service.dto.PeriodicReservationDTO;
import com.mycompany.myapp.service.dto.ReservationDTO;
import com.mycompany.myapp.service.reservation.PeriodicReservationService;
import com.mycompany.myapp.service.reservation.ReservationService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.vm.AdditionalToolsVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReservationResource {

    ReservationService reservationService;
    PeriodicReservationService periodicReservationService;

    public ReservationResource(ReservationService reservationService, PeriodicReservationService periodicReservationService){
        this.reservationService = reservationService;
        this.periodicReservationService = periodicReservationService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserve(@RequestBody ReservationDTO reservationDTO) throws Exception {
        reservationService.reserve(
            reservationDTO.getPoolId(),
            reservationDTO.getCount(),
            reservationDTO.getFrom(),
            reservationDTO.getTo()
        );
        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert( "reservation.accepted", reservationDTO.getPoolId()))
            .build();
    }

    @PostMapping("/reserve-period")
    public ResponseEntity<Void> reservePeriod(@RequestBody PeriodicReservationDTO reservationDTO) throws Exception{
        periodicReservationService.reserve(
            reservationDTO.getPoolId(),
            reservationDTO.getCount(),
            reservationDTO.getFromPeriod(),
            reservationDTO.getToPeriod(),
            reservationDTO.getFromTime(),
            reservationDTO.getToTime(),
            reservationDTO.getLeftOuts()
        );
        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert( "reservation.accepted", reservationDTO.getPoolId()))
            .build();
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

    @PostMapping("/request-tools")
    @Timed
    public ResponseEntity<Void> sendToolsRequest(@Valid @RequestBody AdditionalToolsVM additionalToolsVM) throws Exception {
        reservationService.sendToolsRequest(additionalToolsVM.getPoolId(), additionalToolsVM.getSelectedTools());
        return ResponseEntity.ok().build();
    }
}
