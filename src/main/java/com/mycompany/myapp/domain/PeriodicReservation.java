package com.mycompany.myapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "periodic_reservation")
public class PeriodicReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "poolId")
    private Pool pool;

    @NotNull
    @Column(unique = false, nullable = false)
    private int count;

    @NotNull
    @Column(name = "period_from")
    private LocalDate fromPeriod;

    @NotNull
    @Column(name = "period_to")
    private LocalDate toPeriod;

    @NotNull
    @Column(name = "time_from")
    private LocalDateTime fromTime;

    @NotNull
    @Column(name = "time_to")
    private LocalDateTime toTime;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationLeftOut> leftOuts= new ArrayList<>();

    public PeriodicReservation(
        @NotNull User user,
        @NotNull Pool pool,
        @NotNull int count,
        @NotNull LocalDate fromPeriod,
        @NotNull LocalDate toPeriod,
        @NotNull LocalDateTime fromTime,
        @NotNull LocalDateTime toTime
    ) {
        this.user = user;
        this.pool = pool;
        this.count = count;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public PeriodicReservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(LocalDate fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public LocalDate getToPeriod() {
        return toPeriod;
    }

    public void setToPeriod(LocalDate toPeriod) {
        this.toPeriod = toPeriod;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }

    public List<ReservationLeftOut> getLeftOuts() {
        return leftOuts;
    }

    public void setLeftOuts(List<ReservationLeftOut> leftOuts) {
        this.leftOuts = leftOuts;
    }
}
