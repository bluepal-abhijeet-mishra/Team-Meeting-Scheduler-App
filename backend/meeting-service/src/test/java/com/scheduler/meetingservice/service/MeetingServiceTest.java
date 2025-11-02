package com.scheduler.meetingservice.service;

import com.scheduler.meetingservice.dto.MeetingRequest;
import com.scheduler.meetingservice.dto.MeetingResponse;
import com.scheduler.meetingservice.exception.MeetingNotFoundException;
import com.scheduler.meetingservice.model.Meeting;
import com.scheduler.meetingservice.repository.MeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private KafkaTemplate<String, MeetingResponse> kafkaTemplate;

    @InjectMocks
    private MeetingService meetingService;

    private MeetingRequest meetingRequest;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        meetingRequest = new MeetingRequest();
        meetingRequest.setTitle("Test Meeting");
        meetingRequest.setDescription("Test Description");

        meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("Test Meeting");
        meeting.setDescription("Test Description");
    }

    @Test
    void whenCreateMeeting_thenReturnMeetingResponse() {
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);
        MeetingResponse meetingResponse = meetingService.createMeeting(meetingRequest);
        assertEquals("Test Meeting", meetingResponse.getTitle());
    }

    @Test
    void whenGetMeetingByIdWithExistingId_thenReturnMeetingResponse() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.of(meeting));
        MeetingResponse meetingResponse = meetingService.getMeetingById(1L);
        assertEquals("Test Meeting", meetingResponse.getTitle());
    }

    @Test
    void whenGetMeetingByIdWithNonExistingId_thenThrowMeetingNotFoundException() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MeetingNotFoundException.class, () -> meetingService.getMeetingById(1L));
    }
}
