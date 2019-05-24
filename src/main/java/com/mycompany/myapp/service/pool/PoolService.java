package com.mycompany.myapp.service.pool;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.repository.PoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PoolService {

    private final PoolRepository poolRepository;

    private final FileParser fileParser;

    public PoolService(PoolRepository poolRepository, FileParser fileParser) {
        this.poolRepository = poolRepository;
        this.fileParser = fileParser;
    }

    public java.util.List<Pool> getAllPools(){
        return poolRepository.findAll();
    }

    public Pool getPoolById(String poolId) {
        return poolRepository.getByPoolId(poolId);
    }

    public void loadFile(MultipartFile file) throws IOException{
        poolRepository.saveAll(fileParser.read(file.getInputStream()));
    }

    public Boolean verify(MultipartFile file) throws IOException{
        return fileParser.verify(file.getInputStream());
    }
}
