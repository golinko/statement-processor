package com.golinko.statement.processor.service.reader;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.golinko.statement.processor.dto.MimeType;
import com.golinko.statement.processor.dto.StatementDTO;
import com.golinko.statement.processor.exceptions.StatementProcessorException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.List;

@Slf4j
@Component
@Getter
public class CSVStatementReader implements StatementReader {

    @Value("${demo.csv:classpath:records.csv}")
    private Resource demoData;

    @Override
    public List<StatementDTO> read(Reader reader) throws StatementProcessorException {
        log.debug("read()");
        try (reader) {
            CsvMapper mapper = new CsvMapper().enable(CsvParser.Feature.TRIM_SPACES);
            CsvSchema bootstrapSchema = mapper
                    .typedSchemaFor(StatementDTO.class)
                    .withHeader()
                    .withLineSeparator(",")
                    .withNullValue("NULL");
            MappingIterator<StatementDTO> readValues = mapper
                    .readerFor(StatementDTO.class)
                    .with(bootstrapSchema)
                    .readValues(reader);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error during csv file read", e);
            throw new StatementProcessorException("Error during csv file read");
        }
    }

    @Override
    public boolean supports(MimeType mimeType) {
        return MimeType.CSV.equals(mimeType);
    }

}
