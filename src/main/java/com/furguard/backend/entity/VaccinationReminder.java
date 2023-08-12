package com.furguard.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vaccinationReminderId;

    @ManyToOne
    @JoinColumn(name = "vr_pet_id")
    private PetProfile petProfile;

    @ManyToOne
    @JoinColumn(name = "vr_vaccination_id")
    private Vaccination vaccination;

    @NotNull
    private LocalTime reminderTime;
}
