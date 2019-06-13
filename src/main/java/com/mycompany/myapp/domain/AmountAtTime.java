package com.mycompany.myapp.domain;

import java.util.Date;

public class AmountAtTime {

    private Date date;

    private Integer amount;

    public AmountAtTime(Date date, Integer amount) {
        this.date = date;
        this.amount = amount;
    }

    public AmountAtTime() {
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
