package com.golinko.statement.processor.service.reader;

import com.golinko.statement.processor.dto.MimeType;
import com.golinko.statement.processor.dto.StatementDTO;
import com.golinko.statement.processor.exceptions.StatementProcessorException;
import org.springframework.core.io.Resource;

import java.io.Reader;
import java.util.List;

public interface StatementReader {

    List<StatementDTO> read(Reader reader) throws StatementProcessorException;

    boolean supports(MimeType mimeType);

    Resource getDemoData();
}
