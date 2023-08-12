package com.furguard.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicationId;

    @ManyToOne
    @JoinColumn(name = "m_pet_id")
    private PetProfile petProfile;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal dosage;

    @NotNull
    private String frequency;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;
}
