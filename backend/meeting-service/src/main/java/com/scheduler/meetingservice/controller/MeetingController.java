package com.scheduler.meetingservice.controller;

import com.scheduler.meetingservice.dto.MeetingRequest;
import com.scheduler.meetingservice.dto.MeetingResponse;
import com.scheduler.meetingservice.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
