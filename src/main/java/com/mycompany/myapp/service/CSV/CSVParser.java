package com.mycompany.myapp.service.CSV;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class CSVParser {
    private static final Logger logger = LoggerFactory.getLogger(CSVParser.class);

    public static void loadObjectList(String fileName) {
        try {
            CsvSchema bootstrapSchema =
                CsvSchema.emptySchema().withHeader();
////                    .builder().addArrayColumn("pool_id").addArrayColumn("displayName").addArrayColumn(
////                    "maximumCount").addArrayColumn("enabled").addArrayColumn("description").build();
//

            File file = new ClassPathResource(fileName).getFile();
            Reader reader = new FileReader(file);

            CsvMapper mapper = new CsvMapper();
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//            CsvSchema schema = mapper.schemaFor(ParseToObject.class);
//            String csv = mapper.writer(schema).writeValueAsString(value);

            MappingIterator<ParseToObject> it = mapper.readerFor(ParseToObject.class).with(bootstrapSchema)
                .readValues(reader);

            while (it.hasNextValue()) {
                System.out.println(it.nextValue());
                // ... do something with the value
            }
        } catch (Exception e) {
            logger.error("Error occurred while loading object list from file " + fileName, e);
        }
    }

    public static void main(String[] args) {

        loadObjectList("pcoip_pools.csv");
    }
}
