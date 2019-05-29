package com.mycompany.myapp.web.rest.pool;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.service.pool.PoolService;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PoolResource {

    private final PoolService poolService;


    public PoolResource(PoolService poolService) {
        this.poolService = poolService;
    }

    @GetMapping("/pool/list")
    @Timed
    public ResponseEntity<List<Pool>> getAllPools() {
        return ResponseUtil.wrapOrNotFound(Optional.of(poolService.getAllPools()));
    }

    @PostMapping("/pool/upload")
    @Timed
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).equals(".csv")){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
            }
            if (!poolService.verify(file)) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

            } else {
                poolService.loadFile(file);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PostMapping("/pool/save")
    @Timed
    public ResponseEntity<Void> save(@RequestBody Pool[] pools) {
        for (Pool pool : pools) {
            pool.getTools().forEach(t -> t.setPool(pool));
        }
        try {
            if (!poolService.verify(pools)) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
            } else {
                poolService.save(Arrays.asList(pools));
                return ResponseEntity.ok().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }


    @PostMapping("/pool/parse")
    @Timed
    public ResponseEntity<List<Pool>> parseFile(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).equals(".csv")) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
            }
            return ResponseUtil.wrapOrNotFound(Optional.of(poolService.parse(file)));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }
}
