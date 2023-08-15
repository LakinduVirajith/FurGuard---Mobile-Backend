package com.furguard.backend.controller;

import com.furguard.backend.dto.LocationTrackerDTO;
import com.furguard.backend.entity.LocationTracker;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.PreconditionRequiredException;
import com.furguard.backend.service.LocationTrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("location")
@RequiredArgsConstructor
@Tag(name = "Location Tracker Controller")
public class LocationTrackerController {

    private final LocationTrackerService trackerService;

    @PostMapping("/tracker")
    @Operation(
            summary = "Set Location Tracker",
            description = "Create or update the location tracker for a pet."
    )
    public LocationTrackerDTO setTracker(@Valid @RequestBody LocationTracker tracker) throws PreconditionRequiredException, NotFoundException {
        return trackerService.setTracker(tracker);
    }

    @GetMapping("/tracker")
    @Operation(
            summary = "Fetch Tracker Details",
            description = "Retrieve the details of the pet's location tracker."
    )
    public LocationTrackerDTO fetchTrackerDetails() throws NotFoundException {
        return trackerService.fetchTrackerDetails();
    }

    @PutMapping("/tracker")
    @Operation(
            summary = "Turn Off Tracker",
            description = "Turn off the location tracker for a pet."
    )
    public ResponseEntity setTrackerOff() throws NotFoundException {
        return trackerService.setTrackerOff();
    }

    @DeleteMapping("/tracker")
    @Operation(
            summary = "Remove Tracker",
            description = "Remove the location tracker for a pet."
    )
    public ResponseEntity removeTracker() throws NotFoundException {
        return trackerService.removeTracker();
    }
}
