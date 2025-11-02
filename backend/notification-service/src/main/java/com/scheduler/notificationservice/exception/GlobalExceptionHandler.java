package com.scheduler.notificationservice.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles NotificationSendingException.
     *
     * @param ex      the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NotificationSendingException.class)
    public ResponseEntity<ErrorDetails> handleNotificationSendingException(
            NotificationSendingException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles global exceptions.
     *
     * @param ex      the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
