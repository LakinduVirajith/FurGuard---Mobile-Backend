package com.furguard.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vaccinationId;

    @ManyToOne
    @JoinColumn(name = "v_pet_id")
    private PetProfile petProfile;

    @NotNull
    private String name;

    @NotNull
    private LocalDate administeredDate;

    @NotNull
    private LocalDate expirationDate;

    @OneToMany(mappedBy = "vaccination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VaccinationReminder> vaccinationReminders;
}
