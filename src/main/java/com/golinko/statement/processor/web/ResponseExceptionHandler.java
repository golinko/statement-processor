package com.golinko.statement.processor.web;

import com.golinko.statement.processor.exceptions.ExceptionResponseMessage;
import com.golinko.statement.processor.exceptions.NotFoundException;
import com.golinko.statement.processor.exceptions.StatementProcessorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionResponseMessage handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("HTTP method argument type mismatch: {}", e.getMessage());
        return new ExceptionResponseMessage("The request is invalid.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResponseMessage handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("HTTP request method not supported: {}", e.getMessage());
        return new ExceptionResponseMessage("Request method used is not allowed for this endpoint.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponseMessage handleNotFoundException(NotFoundException e) {
        return new ExceptionResponseMessage(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(StatementProcessorException.class)
    public ExceptionResponseMessage handleStatementProcessorException(StatementProcessorException e) {
        return new ExceptionResponseMessage(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ExceptionResponseMessage handleOtherExceptions(Throwable e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponseMessage("Something went wrong while processing the request.");
    }
}
