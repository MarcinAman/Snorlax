package com.mycompany.myapp.web.rest.tool;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Tool;
import com.mycompany.myapp.service.tool.ToolService;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ToolResource {

    private final ToolService toolService;

    public ToolResource(ToolService toolService){
        this.toolService = toolService;
    }

    @GetMapping("/tool/list/{poolId}")
    @Timed
    public ResponseEntity<List<Tool>> getAdditionalTools(@PathVariable(value = "poolId") String poolId){
        return ResponseUtil.wrapOrNotFound(Optional.of(toolService.getAdditionalTools(poolId)));
    }
}
