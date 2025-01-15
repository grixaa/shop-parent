package org.studing.catalogue.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;
import static org.springframework.http.ResponseEntity.badRequest;

@ControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class BadRequestControllerAdvice {
    MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException exception, Locale locale) {
        val problemDetail = forStatusAndDetail(
            BAD_REQUEST,
            requireNonNull(messageSource.getMessage(
                "errors.400.title",
                new Object[0],
                "errors.400.title",
                locale)));

        problemDetail.setProperty(
            "errors",
            exception.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList());

        return badRequest().body(problemDetail);
    }
}