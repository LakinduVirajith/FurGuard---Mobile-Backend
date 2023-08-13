package com.furguard.backend.entity;

import com.furguard.backend.enums.PetGender;
import com.furguard.backend.enums.PetSpecies;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class PetProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @NotNull
    private String name;

    private String image;

    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PetSpecies species;

    @NotNull
    private String breed;

    @NotNull
    @Min(value = 0, message = "Age cannot be less than 0")
    private Integer age;

    @Min(value = 0, message = "Weight cannot be less than 0")
    private String weight;

    private String color;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PetGender gender;

    @NotNull
    private Boolean isActive = true;

    private LocalDate deactivatedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_user_id")
    private User user;

    @OneToMany(mappedBy = "petProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaccination> vaccinations;

    @OneToMany(mappedBy = "petProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Allergy> allergies;

    @OneToMany(mappedBy = "petProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medication> medications;

    @OneToMany(mappedBy = "petProfile")
    private List<MedicationReminder> medicationReminders;

    @OneToMany(mappedBy = "petProfile")
    private List<VaccinationReminder> vaccinationReminders;

    @OneToOne(mappedBy = "petProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private LostPetNotice lostPetNotice;

    @OneToOne(mappedBy = "petProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private SafeArea safeArea;
}
