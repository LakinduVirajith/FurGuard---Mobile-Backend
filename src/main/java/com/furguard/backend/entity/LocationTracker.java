package com.furguard.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackerId;

    @NotNull
    private double trackerLatitude;

    @NotNull
    private double trackerLongitude;

    @NotNull
    private LocalDateTime lastTrackingTime;

    private Boolean isTrackingEnabled = true;

    @OneToOne
    @JoinColumn(name = "t_safe_area_id")
    private SafeArea safeArea;
}
