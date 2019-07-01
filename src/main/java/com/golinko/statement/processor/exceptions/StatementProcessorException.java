package com.golinko.statement.processor.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class StatementProcessorException extends Exception {

    public StatementProcessorException(@NonNull String message) {
        super(message);
    }

    @Override
    public String toString() {
        return format("Message: %s", getMessage());
    }
}
