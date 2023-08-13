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
public class MedicationReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicationReminderId;

    @ManyToOne
    @JoinColumn(name = "mr_pet_id")
    private PetProfile petProfile;

    @ManyToOne
    @JoinColumn(name = "mr_medication_id")
    private Medication medication;

    @NotNull
    private LocalTime reminderTime;

    private String frequency;
}
