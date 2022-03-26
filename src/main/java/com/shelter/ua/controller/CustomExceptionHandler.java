package com.shelter.ua.controller;

import com.shelter.ua.dto.response.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;
import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.STACK_TRACE;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
@Getter
public class CustomExceptionHandler {

    private final ErrorAttributes errorAttributes;

    private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
        return new HashMap<>(
                errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(MESSAGE, STACK_TRACE)));
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleRuntimeException(
            RuntimeException exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        ExceptionResponse response = new ExceptionResponse(getErrorAttributes(request));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, WebRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request);

        ExceptionResponse response = new ExceptionResponse(errorAttributes);

        response.setMessage("Validation failed");

        List<String> errorMessages = ((List<FieldError>) errorAttributes.get("errors"))
                .stream()
                .map(e -> String.format("%s - %s", e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList());

        response.setErrors(errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
