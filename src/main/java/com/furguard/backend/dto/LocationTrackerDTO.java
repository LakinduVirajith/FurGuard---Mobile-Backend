package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationTrackerDTO {
    private double trackerLatitude;
    private double trackerLongitude;
    private LocalDateTime lastTrackingTime;
    private boolean isTrackingEnabled;
}
