package com.scheduler.notificationservice.exception;

public class NotificationSendingException extends RuntimeException {
    public NotificationSendingException(String message) {
        super(message);
    }
}
