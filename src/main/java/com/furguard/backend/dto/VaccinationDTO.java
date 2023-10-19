package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationDTO {
    Long vaccinationId;

    @NotNull
    String name;

    @NotNull
    LocalDate administeredDate;

    @NotNull
    LocalDate expirationDate;
}
