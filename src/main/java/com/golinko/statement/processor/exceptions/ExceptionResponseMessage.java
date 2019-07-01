package com.golinko.statement.processor.exceptions;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Object carrying the response message on error
 */
@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ExceptionResponseMessage {

    @NonNull
    private String message;
}
