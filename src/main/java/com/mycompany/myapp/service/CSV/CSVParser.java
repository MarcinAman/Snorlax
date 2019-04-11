package com.mycompany.myapp.service.CSV;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mycompany.myapp.domain.Pool;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class CSVParser {
    private static final Logger logger = LoggerFactory.getLogger(CSVParser.class);

    private static List<ParsingContainer> loadObjectList(InputStream file) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            Reader reader = new InputStreamReader(file);
            CsvMapper mapper = new CsvMapper();
            MappingIterator<ParsingContainer> it = mapper.readerFor(ParsingContainer.class).with(bootstrapSchema)
                .readValues(reader);
            return List.ofAll(it.readAll());
        } catch (Exception e) {
            logger.error("Error occurred while loading object list from file ",e);
        }
        return List.empty();
    }

    private static Boolean verify(List<ParsingContainer> parsedObjs, List<String> poolIdInDatabase) {
        Map<String, ParsingContainer> objects =
            parsedObjs.toLinkedMap(parsingContainer -> Tuple.of(parsingContainer.getPoolId(), parsingContainer));
        return poolIdInDatabase
            .forAll(poolId -> !objects.containsKey(poolId));
    }

    public static List<Pool> read(InputStream file, List<String> poolIdInDatabase) {
        List<ParsingContainer> objects = loadObjectList(file);
        if (verify(objects, poolIdInDatabase)) {
            return objects.map(ParsingContainer::toEmptyPool);
        }
        return List.empty();
    }
}
