package com.mycompany.myapp.domain;

import java.util.List;

public class VirtualMachineTimeUsageStatistics {

    private String id;

    private List<AmountAtTime> usage;

    public VirtualMachineTimeUsageStatistics(String id, List<AmountAtTime> usage) {
        this.id = id;
        this.usage = usage;
    }

    public List<AmountAtTime> getUsage() {
        return usage;
    }

    public void setUsage(List<AmountAtTime> usage) {
        this.usage = usage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
