package com.mycompany.myapp.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PeriodicReservationDTO {

    String poolId;
    int count;
    private LocalDate fromPeriod;
    private LocalDate toPeriod;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private List<LocalDate> leftOuts;

    public PeriodicReservationDTO(String poolId, int count, LocalDate fromPeriod, LocalDate toPeriod, LocalDateTime fromTime, LocalDateTime toTime, List<LocalDate> leftOuts) {
        this.poolId = poolId;
        this.count = count;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.leftOuts = leftOuts;
    }

    public PeriodicReservationDTO() {
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
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

    public List<LocalDate> getLeftOuts() {
        return leftOuts;
    }

    public void setLeftOuts(List<LocalDate> leftOuts) {
        this.leftOuts = leftOuts;
    }
}
