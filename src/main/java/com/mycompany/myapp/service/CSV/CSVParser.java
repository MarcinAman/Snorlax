package com.mycompany.myapp.service.CSV;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.domain.Tool;
import com.mycompany.myapp.service.pool.FileParser;
import com.mycompany.myapp.service.reservation.ReservationService;
import io.vavr.collection.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class CSVParser implements FileParser {
    private static final Logger logger = LoggerFactory.getLogger(CSVParser.class);

    private static ReservationService reservationService;

    public CSVParser(ReservationService reservationService) {
        CSVParser.reservationService = reservationService;
    }

    private static List<ParsingContainerDTO> loadObjectList(InputStream file) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            Reader reader = new InputStreamReader(file);
            CsvMapper mapper = new CsvMapper();

            MappingIterator<ParsingContainerDTO> it = mapper
                .readerFor(ParsingContainerDTO.class)
                .with(bootstrapSchema)
                .readValues(reader);
            return List.ofAll(it.readAll());
        } catch (Exception e) {
            logger.error("Error occurred while loading object list from file ", e);
        }
        return List.empty();
    }

    private static List<Tool> toolsForPool(ParsingContainerDTO obj, Pool pool) {
        return List.of(
            obj
                .getDescription()
                .split(", *")
        )
            .filter(tool -> tool.length() > 0)
            .map(tool -> {
                Tool newTool = new Tool();
                if (tool.matches(".*\\(.*\\).*")) {
                    String version = tool.substring(tool.indexOf("(") + 1, tool.indexOf(")"));
                    newTool.setVersion(version);
                    String description = tool.substring(0, tool.indexOf("(") - 1)
                        + tool.substring(Math.min(tool.indexOf(")") + 1, tool.length() - 1), tool.length() - 1);
                    newTool.setName(description);
                } else {
                    newTool.setVersion("");
                    newTool.setName(tool);
                }
                newTool.setPool(pool);
                return newTool;
            });
    }

    private static Boolean verify(List<ParsingContainerDTO> parsedObjs) {
        return parsedObjs.forAll(object -> object.getMaximumCount() >= reservationService.getActiveOrInFutureReservedCount(object.getPoolId()));
    }

    public List<Pool> read(InputStream file) {
        List<ParsingContainerDTO> objects = loadObjectList(file);
        return objects.map(obj -> {
            Pool pool = obj.toEmptyPool();
            pool.setTools(toolsForPool(obj, pool).toJavaList());
            return pool;
        });
    }

    @Override
    public Boolean verify(InputStream file) {
        List<ParsingContainerDTO> objects = loadObjectList(file);
        return verify(objects);
    }

    @Override
    public Boolean verify(Pool[] pools) {
        List<ParsingContainerDTO> objects = List.of(pools).map(pool -> {
            ParsingContainerDTO tmp = new ParsingContainerDTO();
            tmp.setPoolId(pool.getPoolId());
            tmp.setMaximumCount(pool.getMaximumCount());
            tmp.setEnabled(pool.isEnabled());
            tmp.setDisplayName(pool.getDisplayName());
            tmp.setDescription(String.valueOf(pool.getTools()));
            return tmp;
        });
        return verify(objects);
    }
}
