package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemindersDTO {
    private List<MedicationReminderDTO> medicationReminders;
    private List<VaccinationReminderDTO> vaccinationReminders;
}
