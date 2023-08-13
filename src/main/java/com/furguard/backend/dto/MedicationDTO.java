package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDTO {
    Long medicationId;
    String name;
    BigDecimal dosage;
    String frequency;
    LocalDate startDate;
    LocalDate endDate;
}
