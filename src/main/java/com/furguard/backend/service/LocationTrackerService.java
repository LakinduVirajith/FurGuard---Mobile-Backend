package com.furguard.backend.service;

import com.furguard.backend.dto.LocationTrackerDTO;
import com.furguard.backend.entity.LocationTracker;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.PreconditionRequiredException;
import org.springframework.http.ResponseEntity;

public interface LocationTrackerService {
    LocationTrackerDTO setTracker(LocationTracker tracker) throws NotFoundException, PreconditionRequiredException;

    LocationTrackerDTO fetchTrackerDetails() throws NotFoundException;

    ResponseEntity<ResponseMessage> setTrackerOff() throws NotFoundException;

    ResponseEntity<ResponseMessage> removeTracker() throws NotFoundException;
}
