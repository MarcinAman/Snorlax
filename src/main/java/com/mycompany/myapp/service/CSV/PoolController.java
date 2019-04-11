package com.mycompany.myapp.service.CSV;


import com.mycompany.myapp.repository.PoolRepository;
import io.vavr.collection.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PoolController {
    private final PoolRepository poolRepository;

    public PoolController(PoolRepository poolRepository) {
        this.poolRepository = poolRepository;
    }

    @PostMapping(value = "/loadCSV")
    public void loadCSV(@RequestParam("file") MultipartFile file) throws IOException {
        List<String> currentlyReserved = poolRepository.getAllPoolsId();
        poolRepository.saveAll(CSVParser.read(file.getInputStream(), currentlyReserved));
    }
}
