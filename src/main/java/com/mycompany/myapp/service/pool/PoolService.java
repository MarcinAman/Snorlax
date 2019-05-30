package com.mycompany.myapp.service.pool;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.repository.PoolRepository;
import org.springframework.data.jpa.domain.Specification;
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

    public List<Pool> getAllPools(){
        return poolRepository.findAll();
    }

    public List<Pool> getFilteredPools(Specification<Pool> specification) {
        return poolRepository.findAll(specification);
    }

    public Pool getPoolById(String poolId) {
        return poolRepository.getByPoolId(poolId);
    }

    public void loadFile(MultipartFile file) throws IOException{
        io.vavr.collection.List<String> currentlyReserved = poolRepository.getAllPoolId();
        poolRepository.saveAll(fileParser.read(file.getInputStream(), currentlyReserved));
    }

    public Boolean verify(MultipartFile file) throws IOException{
        io.vavr.collection.List<String> currentlyReserved = poolRepository.getAllPoolId();
        return fileParser.verify(file.getInputStream(), currentlyReserved);
    }
}
