package com.mycompany.myapp.service.CSV;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonPropertyOrder({"pool_id", "displayName", "maximumCount", "enabled", "description"})
public class ParseToObject {
    private String pool_id;
    private String displayName;
    private String maximumCount;
    private String enabled;
    private String description;

    public ParseToObject() {
        this.pool_id = "";
        this.displayName = "";
        this.maximumCount = "";
        this.enabled = "";
        this.description = "";
    }

    public void setPool_id(String pool_id) {
        this.pool_id = pool_id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setMaximumCount(String maximumCount) {
        this.maximumCount = maximumCount;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPool_id() {
        return pool_id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMaximumCount() {
        return maximumCount;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getDescription() {
        return description;
    }

    public String toString(){
        return this.pool_id;
    }
}
