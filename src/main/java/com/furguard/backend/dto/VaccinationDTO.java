package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationDTO {
    Long vaccinationId;
    String name;
    LocalDate administeredDate;
    LocalDate expirationDate;
}
