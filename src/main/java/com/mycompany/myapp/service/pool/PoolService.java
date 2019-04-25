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

    public List<Pool> getAllPools(){
        return poolRepository.findAll();
    }

    public void loadFile(MultipartFile file) throws IOException{
        io.vavr.collection.List<String> currentlyReserved = poolRepository.getAllPoolsId();
        poolRepository.saveAll(fileParser.read(file.getInputStream(), currentlyReserved));
    }

    public Boolean verify(MultipartFile file) throws IOException{
        io.vavr.collection.List<String> currentlyReserved = poolRepository.getAllPoolsId();
        return fileParser.verify(file.getInputStream(), currentlyReserved);
    }
}
