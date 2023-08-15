package com.furguard.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SafeArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long safeAreaId;

    @OneToOne
    @JoinColumn(name = "s_pet_id")
    private PetProfile petProfile;

    @OneToOne(mappedBy = "safeArea", cascade = CascadeType.ALL, orphanRemoval = true)
    private LocationTracker locationTracker;

    @NotNull
    private double centerLatitude;

    @NotNull
    private double centerLongitude;

    @NotNull
    private double radius;
}
