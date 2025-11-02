package com.scheduler.meetingservice.service;

import com.scheduler.meetingservice.dto.MeetingRequest;
import com.scheduler.meetingservice.dto.MeetingResponse;
import com.scheduler.meetingservice.exception.MeetingNotFoundException;
import com.scheduler.meetingservice.model.Meeting;
import com.scheduler.meetingservice.repository.MeetingRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private KafkaTemplate<String, MeetingResponse> kafkaTemplate;

    private static final String KAFKA_TOPIC = "meeting-invitations";

    /**
     * Creates a new meeting.
     *
     * @param meetingRequest the meeting request
     * @return the meeting response
     */
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

    /**
     * Gets all meetings.
     *
     * @return the all meetings
     */
    public List<MeetingResponse> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Gets meeting by id.
     *
     * @param id the id
     * @return the meeting by id
     */
    public MeetingResponse getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found with id: " + id));
        return convertToResponse(meeting);
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
