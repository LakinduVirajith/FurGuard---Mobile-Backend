package com.furguard.backend.service;

import com.furguard.backend.chat.ChatMessage;
import com.furguard.backend.chat.MessageType;
import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.LocationTrackerDTO;
import com.furguard.backend.entity.LocationTracker;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.SafeArea;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.PreconditionRequiredException;
import com.furguard.backend.repository.LocationTrackerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationTrackerServiceImpl implements LocationTrackerService{

    private final LocationTrackerRepository trackerRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;
    private final SimpMessageSendingOperations messageTemplate;
    private static final double EARTH_RADIUS = 6371000.0;

    @Override
    public LocationTrackerDTO setTracker(LocationTracker tracker) throws NotFoundException, PreconditionRequiredException {
        PetProfile profile = commonFunctions.getPetProfile();

        if(profile.getSafeArea() == null){
            throw new PreconditionRequiredException("No safe area found for your pet profile to track.");
        }

        LocationTracker existTracker;
        if(profile.getSafeArea().getLocationTracker() != null){
            existTracker = profile.getSafeArea().getLocationTracker();
            existTracker.setTrackerLatitude(tracker.getTrackerLatitude());
            existTracker.setTrackerLongitude(tracker.getTrackerLongitude());
            existTracker.setLastTrackingTime(tracker.getLastTrackingTime());
            existTracker.setIsTrackingEnabled(true);

            trackerRepository.save(existTracker);
            conditionChecker(existTracker, profile.getSafeArea());
            return modelMapper.map(existTracker, LocationTrackerDTO.class);
        }else{
            tracker.setSafeArea(profile.getSafeArea());
            tracker.setIsTrackingEnabled(true);

            trackerRepository.save(tracker);
            conditionChecker(tracker, profile.getSafeArea());
            return modelMapper.map(tracker, LocationTrackerDTO.class);
        }
    }

    @Override
    public LocationTrackerDTO fetchTrackerDetails() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();

        if(profile.getSafeArea().getLocationTracker() == null){
            throw new NotFoundException("No tracker information was found.");
        }

        LocationTracker tracker = profile.getSafeArea().getLocationTracker();
        return modelMapper.map(tracker, LocationTrackerDTO.class);
    }

    @Override
    public ResponseEntity setTrackerOff() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();

        if(profile.getSafeArea().getLocationTracker() == null){
            throw new NotFoundException("No tracker information was found.");
        }

        LocationTracker tracker = profile.getSafeArea().getLocationTracker();
        tracker.setIsTrackingEnabled(false);
        trackerRepository.save(tracker);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("tracker set off successfully");

        return ResponseEntity.ok().body(successResponse);
    }

    @Override
    public ResponseEntity removeTracker() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();

        if(profile.getSafeArea().getLocationTracker() == null){
            throw new NotFoundException("No tracker information was found.");
        }

        LocationTracker tracker = profile.getSafeArea().getLocationTracker();
        tracker.setSafeArea(null);
        trackerRepository.delete(tracker);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("tracker details removed successfully");

        return ResponseEntity.ok().body(successResponse);
    }

    public void conditionChecker(LocationTracker tracker, SafeArea safeArea) {
        double distance = checkIfInsideSafeArea(tracker, safeArea);

        if (distance >= safeArea.getRadius()) {
            sendAlertToFrontend(tracker.getSafeArea().getPetProfile().getName(), distance, tracker);
        }
    }

    private double checkIfInsideSafeArea(LocationTracker tracker, SafeArea safeArea) {
        double distance = calculateDistance(
                tracker.getTrackerLatitude(), tracker.getTrackerLongitude(),
                safeArea.getCenterLatitude(), safeArea.getCenterLongitude()
        );

        return distance;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a =  Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private void sendAlertToFrontend(String name, double trackerDistance, LocationTracker tracker) {
        String userDestination = "/user/queue/alert";

        // Create an alert message
        var alertMessage = ChatMessage.builder()
                .type(MessageType.ALERT)
                .content("Tracker is outside the safe area!")
                .sender(name)
                .trackerLatitude(tracker.getTrackerLatitude())
                .trackerLongitude(tracker.getTrackerLongitude())
                .distance(trackerDistance)
                .lastTrackingTime(tracker.getLastTrackingTime())
                .build();

        // Send the alert message to the user's queue using WebSocket
        messageTemplate.convertAndSend(userDestination, alertMessage);
    }
}
