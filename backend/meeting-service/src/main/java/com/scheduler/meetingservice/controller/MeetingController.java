package com.scheduler.meetingservice.controller;

import com.scheduler.meetingservice.dto.MeetingRequest;
import com.scheduler.meetingservice.dto.MeetingResponse;
import com.scheduler.meetingservice.service.MeetingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(@RequestBody MeetingRequest meetingRequest) {
        MeetingResponse meetingResponse = meetingService.createMeeting(meetingRequest);
        return ResponseEntity.ok(meetingResponse);
    }

    @GetMapping
    public ResponseEntity<List<MeetingResponse>> getAllMeetings() {
        List<MeetingResponse> meetings = meetingService.getAllMeetings();
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingResponse> getMeetingById(@PathVariable Long id) {
        MeetingResponse meeting = meetingService.getMeetingById(id);
        return ResponseEntity.ok(meeting);
    }
}
