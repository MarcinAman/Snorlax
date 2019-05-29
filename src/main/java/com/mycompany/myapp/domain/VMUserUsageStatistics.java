package com.mycompany.myapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vm_user_usage_statistics")
public class VMUserUsageStatistics {
    @Id
    @Column(name = "login")
    private String username;

    @Column(name = "times_used")
    private Integer timesUsed;

    public String getUsername() {
        return username;
    }

    public Integer getTimesUsed() {
        return timesUsed;
    }
}
