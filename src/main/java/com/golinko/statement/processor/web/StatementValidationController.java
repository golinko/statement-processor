package com.golinko.statement.processor.web;

import com.golinko.statement.processor.dto.MimeType;
import com.golinko.statement.processor.dto.StatementDTO;
import com.golinko.statement.processor.dto.StatementValidationDTO;
import com.golinko.statement.processor.exceptions.StatementProcessorException;
import com.golinko.statement.processor.service.StatementValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = "/validate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Api(value = "Statements Validation API")
public class StatementValidationController {

    @NonNull
    private final StatementValidationService statementValidationService;

    @ApiOperation(value = "Validate uploaded data by mimeType", response = Set.class)
    @PostMapping("/{mimeType}")
    public ResponseEntity<Set<StatementValidationDTO>> validateStatements(
            @PathVariable MimeType mimeType,
            @RequestBody MultipartFile dataFile) throws StatementProcessorException {
        log.debug("validateStatements({}, {})", mimeType, dataFile.getName());

        try {
            List<StatementDTO> statements = statementValidationService.readStatements(dataFile.getInputStream(), mimeType);
            Set<StatementValidationDTO> result = statementValidationService.validate(statements);

            return new ResponseEntity<>(result, CollectionUtils.isEmpty(result) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            log.error("Error reading uploaded data", e);
            throw new StatementProcessorException("Cannot read uploaded data");
        }
    }
}
