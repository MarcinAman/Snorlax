package com.mycompany.myapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "reservation_leftout")
public class ReservationLeftOut {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reservationId")
    private PeriodicReservation reservation;

    @NotNull
    @Column(name = "day")
    private LocalDate day;

    public ReservationLeftOut(
        @NotNull PeriodicReservation reservation,
        @NotNull LocalDate day
    ) {
        this.reservation = reservation;
        this.day = day;
    }

    public ReservationLeftOut() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PeriodicReservation getReservation() {
        return reservation;
    }

    public void setReservation(PeriodicReservation reservation) {
        this.reservation = reservation;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
}
