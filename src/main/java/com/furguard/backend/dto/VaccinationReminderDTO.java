package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationReminderDTO {
    private Long vaccinationReminderId;
    private LocalTime reminderTime;
}
