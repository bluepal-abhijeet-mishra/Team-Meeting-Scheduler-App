package com.scheduler.meetingservice.exception;

public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException(String message) {
        super(message);
    }
}
