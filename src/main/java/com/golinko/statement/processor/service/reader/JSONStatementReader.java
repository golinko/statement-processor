package com.golinko.statement.processor.service.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golinko.statement.processor.dto.MimeType;
import com.golinko.statement.processor.dto.StatementDTO;
import com.golinko.statement.processor.exceptions.StatementProcessorException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class JSONStatementReader implements StatementReader {

    @Value("${demo.json:classpath:records.json}")
    private Resource demoData;

    @NonNull
    @Qualifier("objectMapper")
    private final ObjectMapper objectMapper;

    @Override
    public List<StatementDTO> read(Reader reader) throws StatementProcessorException {
        log.debug("read()");
        try (reader) {
            return objectMapper.readValue(reader, new TypeReference<List<StatementDTO>>() {});
        } catch (Exception e) {
            log.error("Error during json file read", e);
            throw new StatementProcessorException("Error during json file read");
        }
    }

    @Override
    public boolean supports(MimeType mimeType) {
        return MimeType.JSON.equals(mimeType);
    }

}
