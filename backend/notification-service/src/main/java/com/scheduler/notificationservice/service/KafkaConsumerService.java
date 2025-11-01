package com.scheduler.notificationservice.service;

import com.scheduler.notificationservice.dto.MeetingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "meeting-invitations", groupId = "notification-group")
    public void consume(MeetingResponse meeting) {
        emailService.sendMeetingInvitation(meeting);
    }
}
