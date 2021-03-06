package com.mycompany.myapp.service.pool;

import com.mycompany.myapp.domain.Pool;
import io.vavr.collection.List;

import java.io.InputStream;

public interface FileParser {

    List<Pool> read (InputStream file);

    Boolean verify (InputStream file);

    Boolean verify (Pool[] pools);
}
