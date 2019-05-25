package com.mycompany.myapp.web.rest.pool;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.repository.specification.PoolSpecificationBuilder;
import com.mycompany.myapp.service.pool.PoolService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class PoolResource {

    private final Logger log = LoggerFactory.getLogger(PoolResource.class);

    private final PoolService poolService;

    public PoolResource(PoolService poolService) {
        this.poolService = poolService;
    }

    @GetMapping("/pool/list")
    @Timed
    public ResponseEntity<List<Pool>> getAllPools() {
        return ResponseUtil.wrapOrNotFound(Optional.of(poolService.getAllPools()));
    }

    @GetMapping("/pool/list-filter")
    @Timed
    public ResponseEntity<List<Pool>> filterPools(@RequestParam("search") String search) {

        PoolSpecificationBuilder builder = new PoolSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(search + ",");
        while(matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Pool> specification = builder.build();

        return ResponseUtil.wrapOrNotFound(Optional.of(poolService.getFilteredPools(specification)));
    }

    @PostMapping("/pool/upload")
    @Timed
    public ResponseEntity<Void> uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            if (!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).equals(".csv")){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
            }
            if (!poolService.verify(file)) {
//            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Failed to upload");
                // TODO temporary fix, exception above gets wrapped in status 500 instead of being 406
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
            } else {
                poolService.loadFile(file);
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("Uploaded file", file.getName())).build();
            }
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }
}
