package com.scheduler.meetingservice.service;

import com.scheduler.meetingservice.dto.MeetingRequest;
import com.scheduler.meetingservice.dto.MeetingResponse;
import com.scheduler.meetingservice.model.Meeting;
import com.scheduler.meetingservice.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private KafkaTemplate<String, MeetingResponse> kafkaTemplate;

    private static final String KAFKA_TOPIC = "meeting-invitations";

    public MeetingResponse createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingRequest.getTitle());
        meeting.setDescription(meetingRequest.getDescription());
        meeting.setStartTime(meetingRequest.getStartTime());
        meeting.setEndTime(meetingRequest.getEndTime());
        meeting.setParticipants(meetingRequest.getParticipants());

        Meeting savedMeeting = meetingRepository.save(meeting);
        MeetingResponse meetingResponse = convertToResponse(savedMeeting);

        kafkaTemplate.send(KAFKA_TOPIC, meetingResponse);

        return meetingResponse;
    }

    public List<MeetingResponse> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private MeetingResponse convertToResponse(Meeting meeting) {
        return new MeetingResponse(
                meeting.getId(),
                meeting.getTitle(),
                meeting.getDescription(),
                meeting.getStartTime(),
                meeting.getEndTime(),
                meeting.getParticipants()
        );
    }
}
