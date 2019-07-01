package com.golinko.statement.processor.dto.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
public class LocalDateConverter extends StdConverter<String, LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate convert(@Nullable String source) {
        log.debug("convert({})", source);
        LocalDate result = Optional.ofNullable(source)
                .map(s -> LocalDate.parse(s, formatter))
                .orElse(null);
        log.debug("Result: {}", result);
        return result;
    }
}
