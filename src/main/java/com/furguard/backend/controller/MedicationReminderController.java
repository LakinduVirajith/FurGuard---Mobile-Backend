package com.furguard.backend.controller;

import com.furguard.backend.dto.MedicationReminderDTO;
import com.furguard.backend.entity.MedicationReminder;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.service.MedicationReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "Medication Reminder Controller")
public class MedicationReminderController {

    private final MedicationReminderService reminderService;

    @PostMapping("medication/reminder/{id}")
    @Operation(
            summary = "Add Medication Reminder",
            description = "Add a medication reminder for a specific medication by providing the necessary details."
    )
    public MedicationReminderDTO addReminder(@PathVariable("id") Long medicationId, @Valid @RequestBody MedicationReminder reminder) throws NotFoundException {
        return reminderService.addReminder(medicationId, reminder);
    }

    @GetMapping("medication/reminder/all/{id}")
    @Operation(
            summary = "Fetch All Medication Reminders",
            description = "Retrieve a list of all medication reminders for a specific medication."
    )
    public List<MedicationReminderDTO> fetchAllReminders(@PathVariable("id") Long medicationId) throws NotFoundException, UnauthorizedAccessException {
        return reminderService.fetchAllReminders(medicationId);
    }

    @GetMapping("medication/reminder/{id}")
    @Operation(
            summary = "Fetch Medication Reminder",
            description = "Retrieve details of a specific medication reminder using its unique ID."
    )
    public MedicationReminderDTO fetchReminder(@PathVariable("id") Long reminderId) throws UnauthorizedAccessException, NotFoundException {
        return reminderService.fetchReminder(reminderId);
    }

    @PutMapping("medication/reminder/{id}")
    @Operation(
            summary = "Update Medication Reminder",
            description = "Update details of a specific medication reminder using its unique ID and the updated information."
    )
    public MedicationReminderDTO updateReminder(@PathVariable("id") Long reminderId, @RequestBody MedicationReminder reminder) throws UnauthorizedAccessException, NotFoundException {
        return reminderService.updateReminder(reminderId, reminder);
    }

    @DeleteMapping("medication/reminder/{id}")
    @Operation(
            summary = "Remove Medication Reminder",
            description = "Remove a specific medication reminder using its unique ID."
    )
    public ResponseEntity removeReminder(@PathVariable("id") Long reminderId) throws UnauthorizedAccessException, NotFoundException {
        return reminderService.removeReminder(reminderId);
    }
}
