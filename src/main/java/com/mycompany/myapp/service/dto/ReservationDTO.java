package com.mycompany.myapp.service.dto;

import java.util.Date;

public class ReservationDTO {

    String poolId;
    int count;
    private Date to;
    private Date from;

    public ReservationDTO(String poolId, int count, Date to, Date from) {
        this.poolId = poolId;
        this.count = count;
        this.to = to;
        this.from = from;
    }

    public ReservationDTO() {
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

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }
}
