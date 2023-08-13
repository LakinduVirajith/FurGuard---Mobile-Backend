package com.furguard.backend.controller;

import com.furguard.backend.dto.MedicationDTO;
import com.furguard.backend.entity.Medication;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.service.MedicationService;
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
@Tag(name = "Medication Controller")
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping("/medication")
    @Operation(
            summary = "Add a New Medication",
            description = "Record a new medication for your pet by providing the required details."
    )
    public MedicationDTO addMedication(@Valid @RequestBody Medication medication) throws NotFoundException {
        return medicationService.addMedication(medication);
    }

    @GetMapping("/medication")
    @Operation(
            summary = "Get All Medications",
            description = "Retrieve a list of all medications recorded for your pet."
    )
    public List<MedicationDTO> getAllMedication() throws NotFoundException {
        return medicationService.getAllMedication();
    }

    @GetMapping("/medication/{id}")
    @Operation(
            summary = "Get Medication Details",
            description = "Retrieve detailed information about a specific medication by providing its unique ID."
    )
    public MedicationDTO getMedication(@PathVariable("id") Long medicationId) throws NotFoundException {
        return medicationService.getMedication(medicationId);
    }

    @PutMapping("/medication/{id}")
    @Operation(
            summary = "Update Medication",
            description = "Update details of a specific medication using its unique ID and the updated information."
    )
    public MedicationDTO updateMedication(@PathVariable("id") Long medicationId, @Valid @RequestBody Medication medication) throws UnauthorizedAccessException, NotFoundException {
        return medicationService.updateMedication(medicationId, medication);
    }

    @DeleteMapping("/medication/{id}")
    @Operation(
            summary = "Delete Medication",
            description = "Delete a specific medication record using its unique ID."
    )
    public ResponseEntity deleteMedication(@PathVariable("id") Long medicationId) throws NotFoundException {
        return medicationService.deleteMedication(medicationId);
    }
}
