package com.golinko.statement.processor.web;

import com.golinko.statement.processor.dto.MimeType;
import com.golinko.statement.processor.dto.StatementDTO;
import com.golinko.statement.processor.dto.StatementValidationDTO;
import com.golinko.statement.processor.exceptions.StatementProcessorException;
import com.golinko.statement.processor.service.StatementValidationService;
import com.golinko.statement.processor.service.reader.StatementReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = "/demo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Api(value = "Demo Validation API")
public class StatementDemoController {

    @NonNull
    private final StatementValidationService statementValidationService;

    @ApiOperation(value = "Validate demo data by mimeType", response = Set.class)
    @GetMapping("/{mimeType}")
    public ResponseEntity<Set<StatementValidationDTO>> validateDemoStatements(@PathVariable MimeType mimeType) throws StatementProcessorException {
        log.debug("getStatements({})", mimeType);

        List<StatementDTO> demoStatements = getDemoStatements(mimeType);
        Set<StatementValidationDTO> result = statementValidationService.validate(demoStatements);

        return new ResponseEntity<>(result, CollectionUtils.isEmpty(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    private List<StatementDTO> getDemoStatements(MimeType mimeType) throws StatementProcessorException {
        StatementReader statementReader = statementValidationService.getStatementReader(mimeType);

        return statementReader.read(getDemoReader(statementReader.getDemoData()));
    }

    private Reader getDemoReader(Resource data) throws StatementProcessorException {
        try {
            Path path = Paths.get(data.getURI());
            return Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new StatementProcessorException("This is embarrassing: Demo data could not be read");
        }
    }
}
