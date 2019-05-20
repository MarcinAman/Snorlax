package com.mycompany.myapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pool")
public class Pool {

    @Id
    @Column(name = "poolId")
    private String poolId;

    @NotNull
    @Column(name = "displayName", unique = false, nullable = false)
    private String displayName;

    @NotNull
    @Column(name = "maximumCount", unique = false, nullable = false)
    private int maximumCount;

    @NotNull
    @Column(name = "enabled", unique = false, nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "pool")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "pool",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tool> tools = new ArrayList<>();


    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public int getMaximumCount() {
        return maximumCount;
    }

    public void setMaximumCount(int maximumCount) {
        this.maximumCount = maximumCount;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }
}
