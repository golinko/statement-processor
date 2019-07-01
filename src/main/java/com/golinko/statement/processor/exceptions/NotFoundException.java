package com.golinko.statement.processor.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends StatementProcessorException {

    public NotFoundException(@NonNull String message) {
        super(message);
    }
}
