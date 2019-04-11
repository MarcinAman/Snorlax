package com.mycompany.myapp.service.CSV;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.service.pool.FileParser;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CSVParser implements FileParser{
    private static final Logger logger = LoggerFactory.getLogger(CSVParser.class);

    public List<Pool> read(InputStream file, List<String> poolIdInDatabase) {
        List<ParsingContainerDTO> objects = loadObjectList(file);
        if (verify(objects, poolIdInDatabase)) {
            return objects.map(ParsingContainerDTO::toEmptyPool);
        }
        return List.empty();
    }

    private static List<ParsingContainerDTO> loadObjectList(InputStream file) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            Reader reader = new InputStreamReader(file);
            CsvMapper mapper = new CsvMapper();
            MappingIterator<ParsingContainerDTO> it = mapper.readerFor(ParsingContainerDTO.class).with(bootstrapSchema)
                .readValues(reader);
            return List.ofAll(it.readAll());
        } catch (Exception e) {
            logger.error("Error occurred while loading object list from file ",e);
        }
        return List.empty();
    }

    private static Boolean verify(List<ParsingContainerDTO> parsedObjs, List<String> poolIdInDatabase) {
        Map<String, ParsingContainerDTO> objects =
            parsedObjs.toLinkedMap(parsingContainerDTO -> Tuple.of(parsingContainerDTO.getPoolId(), parsingContainerDTO));
        return poolIdInDatabase
            .forAll(poolId -> !objects.containsKey(poolId));
    }
}
