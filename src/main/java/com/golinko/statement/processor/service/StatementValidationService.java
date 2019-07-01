package com.golinko.statement.processor.service;

import com.golinko.statement.processor.dto.MimeType;
import com.golinko.statement.processor.dto.StatementDTO;
import com.golinko.statement.processor.dto.StatementValidationDTO;
import com.golinko.statement.processor.exceptions.StatementProcessorException;
import com.golinko.statement.processor.service.reader.StatementReader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatementValidationService {

    @NonNull
    private List<StatementReader> statementReaders;

    public List<StatementDTO> readStatements(InputStream inputStream, MimeType mimeType) throws StatementProcessorException {
        return getStatementReader(mimeType).read(new InputStreamReader(inputStream));
    }

    public Set<StatementValidationDTO> validate(List<StatementDTO> statements) {
        Set<StatementValidationDTO> result = new HashSet<>();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Map<@NonNull Long, List<StatementDTO>> mapByReference = statements.stream()
                .collect(groupingBy(
                        StatementDTO::getReference,
                        mapping(identity(), toList())));

        mapByReference.forEach((reference, list) -> {
            StatementValidationDTO dto = StatementValidationDTO.builder().reference(reference).build();
            if (list.size() > 1) {
                dto.getErrors().putIfAbsent("reference", new ArrayList<>());
                dto.getErrors().get("reference").add("not unique");
            }

            list.forEach(s -> {
                Set<ConstraintViolation<StatementDTO>> vv = validator.validate(s);
                vv.forEach(v -> {
                    String property = v.getPropertyPath().toString();
                    dto.getErrors().putIfAbsent(property, new ArrayList<>());
                    dto.getErrors().get(property).add(v.getMessage());
                });
            });

            if (!dto.getErrors().isEmpty()) {
                result.add(dto);
            }
        });

        return result;
    }

    public StatementReader getStatementReader(MimeType mimeType) throws StatementProcessorException {
        return statementReaders.stream()
                .filter(s -> s.supports(mimeType))
                .findFirst()
                .orElseThrow(() -> new StatementProcessorException("There is no statement reader for mimeType"));
    }
}
