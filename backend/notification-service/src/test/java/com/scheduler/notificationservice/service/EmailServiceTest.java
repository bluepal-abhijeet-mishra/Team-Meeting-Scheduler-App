package com.scheduler.notificationservice.service;

import com.scheduler.notificationservice.dto.MeetingResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void whenSendMeetingInvitation_thenMessageIsSent() {
        MeetingResponse meetingResponse = new MeetingResponse(
                1L,
                "Test Meeting",
                "Test Description",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                Collections.singletonList("test@test.com")
        );
        emailService.sendMeetingInvitation(meetingResponse);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("test@test.com");
        message.setSubject("Meeting Invitation: Test Meeting");
        message.setText("You have been invited to a meeting.\n\n" +
                "Title: Test Meeting\n" +
                "Description: Test Description\n" +
                "Start Time: " + meetingResponse.getStartTime() + "\n" +
                "End Time: " + meetingResponse.getEndTime());
        verify(emailSender).send(message);
    }
}
