package com.mycompany.myapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vm_usage_statistics")
public class VMUsageStatistics {
    @Id
    @Column(name = "pool_id")
    private String poolId;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "times_used")
    private Integer timesUsed;

    public String getPoolId() {
        return poolId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getTimesUsed() {
        return timesUsed;
    }
}
