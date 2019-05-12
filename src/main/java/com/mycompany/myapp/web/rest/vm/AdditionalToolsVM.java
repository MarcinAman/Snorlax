package com.mycompany.myapp.web.rest.vm;

import java.util.List;

public class AdditionalToolsVM {
    private String poolId;

    private List<String> selectedTools;

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public List<String> getSelectedTools() {
        return selectedTools;
    }

    public void setSelectedTools(List<String> selectedTools) {
        this.selectedTools = selectedTools;
    }
}
