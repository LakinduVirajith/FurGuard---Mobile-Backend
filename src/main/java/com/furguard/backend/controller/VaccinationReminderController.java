package com.furguard.backend.controller;

import com.furguard.backend.dto.VaccinationReminderDTO;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.VaccinationReminder;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.service.VaccinationReminderService;
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
@Tag(name = "Vaccination Reminder Controller")
public class VaccinationReminderController {

    private final VaccinationReminderService reminderService;

    @PostMapping("vaccination/reminder/{id}")
    @Operation(
            summary = "Add Vaccination Reminder",
            description = "Add a vaccination reminder for a specific vaccination by providing the necessary details."
    )
    public VaccinationReminderDTO addVaccination(@PathVariable("id") Long vaccinationId, @Valid @RequestBody VaccinationReminder reminder) throws NotFoundException {
        return reminderService.addVaccination(vaccinationId, reminder);
    }

    @GetMapping("vaccination/reminder/all/{id}")
    @Operation(
            summary = "Fetch All Vaccination Reminders",
            description = "Retrieve a list of all vaccination reminders for a specific vaccination."
    )
    public List<VaccinationReminderDTO> fetchAllVaccinations(@PathVariable("id") Long vaccinationId) throws NotFoundException, UnauthorizedAccessException {
        return reminderService.fetchAllVaccinations(vaccinationId);
    }

    @GetMapping("vaccination/reminder/{id}")
    @Operation(
            summary = "Fetch Vaccination Reminder",
            description = "Retrieve details of a specific vaccination reminder using its unique ID."
    )
    public VaccinationReminderDTO fetchVaccination(@PathVariable("id") Long reminderId) throws UnauthorizedAccessException, NotFoundException {
        return reminderService.fetchVaccination(reminderId);
    }

    @PutMapping("vaccination/reminder/{id}")
    @Operation(
            summary = "Update Vaccination Reminder",
            description = "Update details of a specific vaccination reminder using its unique ID and the updated information."
    )
    public VaccinationReminderDTO updateVaccination(@PathVariable("id") Long reminderId, @RequestBody VaccinationReminder reminder) throws UnauthorizedAccessException, NotFoundException {
        return reminderService.updateVaccination(reminderId, reminder);
    }

    @DeleteMapping("vaccination/reminder/{id}")
    @Operation(
            summary = "Remove Vaccination Reminder",
            description = "Remove a specific vaccination reminder using its unique ID."
    )
    public ResponseEntity<ResponseMessage> removeVaccination(@PathVariable("id") Long reminderId) throws UnauthorizedAccessException, NotFoundException {
        return reminderService.removeVaccination(reminderId);
    }
}
