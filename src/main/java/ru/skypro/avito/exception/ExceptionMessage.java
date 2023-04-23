package ru.skypro.avito.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ExceptionMessage {

    private final int statusCode;

    private final LocalDateTime timestamp;

    private final String message;

    private final String description;

    private ExceptionMessage(HttpStatus httpStatus, String message, String description) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = httpStatus.value();
        this.message = message;
        this.description = description;
    }

    public static ExceptionMessage of(HttpStatus httpStatus, String message, String description) {
        return new ExceptionMessage(httpStatus, message, description);
    }
}
