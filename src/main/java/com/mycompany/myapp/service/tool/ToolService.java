package com.mycompany.myapp.service.tool;

import com.mycompany.myapp.domain.Tool;
import com.mycompany.myapp.repository.ToolRepository;
import com.mycompany.myapp.service.pool.PoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    private final ToolRepository toolRepository;

    private final PoolService poolService;

    public ToolService(ToolRepository toolRepository, PoolService poolService){
        this.toolRepository = toolRepository;
        this.poolService = poolService;
    }

    public List<Tool> getAdditionalTools(String poolId){
        return toolRepository.getAllByPoolIsNotIn(poolService.getPoolById(poolId));
    }
}
