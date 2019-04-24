package com.mycompany.myapp.web.rest.pool;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.service.pool.PoolService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PoolResource {

    private final PoolService poolService;

    public PoolResource(PoolService poolService) {
        this.poolService = poolService;
    }

    @GetMapping("/pools")
    @Timed
    public ResponseEntity<List<Pool>> getAllPools() {
        return ResponseUtil.wrapOrNotFound(Optional.of(poolService.getAllPools()));
    }

    @PostMapping("/pools/upload")
    @Timed
    public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        poolService.loadFile(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());

        return "redirect:/api/pools";
    }
}
