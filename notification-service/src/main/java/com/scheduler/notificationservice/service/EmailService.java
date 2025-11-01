package com.scheduler.notificationservice.service;

import com.scheduler.notificationservice.dto.MeetingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMeetingInvitation(MeetingResponse meeting) {
        for (String participant : meeting.getParticipants()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(participant);
            message.setSubject("Meeting Invitation: " + meeting.getTitle());
            message.setText("You have been invited to a meeting.\n\n" +
                    "Title: " + meeting.getTitle() + "\n" +
                    "Description: " + meeting.getDescription() + "\n" +
                    "Start Time: " + meeting.getStartTime() + "\n" +
                    "End Time: " + meeting.getEndTime());
            mailSender.send(message);
        }
    }
}
