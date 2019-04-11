package com.mycompany.myapp.service.CSV;

import com.mycompany.myapp.domain.Pool;

import java.util.LinkedList;

public class ParsingContainer {
    private String poolId;
    private String displayName;
    private Integer maximumCount;
    private Boolean enabled;
    private String description;

    public ParsingContainer() {

    }

    //    ugly but CSV file format is that ugly
    public void setPool_id(String pool_id) {
        this.poolId = pool_id;
    }

    public void setPoolId(String pool_id) {
        this.poolId = pool_id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setMaximumCount(Integer maximumCount) {
        this.maximumCount = maximumCount;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //    ugly but CSV file format is that ugly
    public String getPool_id() {
        return poolId;
    }

    public String getPoolId() {
        return poolId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getMaximumCount() {
        return maximumCount;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return this.poolId + " " + this.displayName + " " + this.enabled + " " + this.maximumCount;
    }

    public Pool toEmptyPool() {
        Pool pool = new Pool();
        pool.setDisplayName(displayName);
        pool.setEnabled(enabled);
        pool.setMaximumCount(maximumCount);
        pool.setPoolId(poolId);
        pool.setTools(new LinkedList<>());
        pool.setReservations(new LinkedList<>());
        return pool;
    }

    public Pool toPool(Pool oldPool) {
        Pool pool = new Pool();
        pool.setDisplayName(displayName);
        pool.setEnabled(enabled);
        pool.setMaximumCount(maximumCount);
        pool.setPoolId(poolId);
        pool.setTools(oldPool.getTools());
        pool.setReservations(oldPool.getReservations());
        return pool;
    }
}
