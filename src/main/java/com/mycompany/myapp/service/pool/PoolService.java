package com.mycompany.myapp.service.pool;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.repository.PoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PoolService {

    private final PoolRepository poolRepository;

    private final FileParser fileParser;

    public PoolService(PoolRepository poolRepository, FileParser fileParser) {
        this.poolRepository = poolRepository;
        this.fileParser = fileParser;
    }

    public java.util.List<Pool> getAllPools() {
        return poolRepository.findAll();
    }

    public Pool getPoolById(String poolId) {
        return poolRepository.getByPoolId(poolId);
    }

    public void loadFile(MultipartFile file) throws IOException {
        save(parse(file));
    }

    public Boolean verify(MultipartFile file) throws IOException{
        return fileParser.verify(file.getInputStream());
    }

    public Boolean verify(Pool[] pools) {
        return fileParser.verify(pools);
    }

    public List<Pool> parse(MultipartFile file) throws IOException {
        return fileParser.read(file.getInputStream()).toJavaList();
    }

    public void save(List<Pool> pools) {
        poolRepository.saveAll(pools);
    }
}
