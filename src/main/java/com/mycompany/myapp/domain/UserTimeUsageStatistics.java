package com.mycompany.myapp.domain;

import java.util.List;

public class UserTimeUsageStatistics {

    private Long id;

    private List<AmountAtTime> usage;

    public UserTimeUsageStatistics(Long id, List<AmountAtTime> usage) {
        this.id = id;
        this.usage = usage;
    }

    public List<AmountAtTime> getUsage() {
        return usage;
    }

    public void setUsage(List<AmountAtTime> usage) {
        this.usage = usage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
