package com.golinko.statement.processor.service.reader;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
public class XMLStatementReader implements StatementReader {

    @Value("${demo.xml:classpath:records.xml}")
    private Resource demoData;

    @Override
    public List<StatementDTO> read(Reader reader) throws StatementProcessorException {
        log.debug("read()");
        try (reader) {
            XmlMapper mapper = new XmlMapper();
            MappingIterator<StatementDTO> readValues = mapper
                    .readerFor(StatementDTO.class)
                    .readValues(reader);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error during xml file read", e);
            throw new StatementProcessorException("Error during xml file read");
        }
    }

    @Override
    public boolean supports(MimeType mimeType) {
        return MimeType.XML.equals(mimeType);
    }
}
