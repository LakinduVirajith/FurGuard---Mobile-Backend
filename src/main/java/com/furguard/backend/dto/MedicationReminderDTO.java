package com.furguard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationReminderDTO {

    private Long medicationReminderId;
    
    private LocalTime reminderTime;

    private String frequency;
}
