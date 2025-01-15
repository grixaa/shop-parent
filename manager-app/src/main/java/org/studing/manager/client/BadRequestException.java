package org.studing.manager.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(makeFinal = true)
public class BadRequestException extends RuntimeException {
    List<String> errors;

    public BadRequestException(List<String> errors) {
        this.errors = errors;
    }

    public BadRequestException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public BadRequestException(String message, Throwable cause, List<String> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public BadRequestException(Throwable cause, List<String> errors) {
        super(cause);
        this.errors = errors;
    }

    public BadRequestException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        List<String> errors) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errors = errors;
    }
}
