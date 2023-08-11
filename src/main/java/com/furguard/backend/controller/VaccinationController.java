package com.furguard.backend.controller;

import com.furguard.backend.dto.VaccinationDTO;
import com.furguard.backend.entity.Vaccination;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.service.VaccinationService;
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
@Tag(name = "Vaccination Controller")
public class VaccinationController {

    private final VaccinationService vaccinationService;

    @PostMapping("/vaccination")
    @Operation(
            summary = "Add Vaccination",
            description = "Record a new vaccination for your pet by providing the necessary details."
    )
    public VaccinationDTO addVaccination(@Valid @RequestBody Vaccination vaccination) throws NotFoundException {
        return vaccinationService.addVaccination(vaccination);
    }

    @GetMapping("/vaccination")
    @Operation(
            summary = "Get All Vaccinations",
            description = "Retrieve a list of all vaccinations recorded for your pet."
    )
    public List<VaccinationDTO> getAllVaccination() throws NotFoundException {
        return vaccinationService.getAllVaccination();
    }

    @GetMapping("/vaccination/{id}")
    @Operation(
            summary = "Get Vaccination Details",
            description = "Retrieve details of a specific vaccination by providing its unique ID."
    )
    public VaccinationDTO getVaccination(@PathVariable("id") Long vaccinationId) throws NotFoundException {
        return vaccinationService.getVaccination(vaccinationId);
    }

    @PutMapping("/vaccination/{id}")
    @Operation(
            summary = "Update Vaccination",
            description = "Update details of a specific vaccination using its unique ID and the updated information."
    )
    public VaccinationDTO updateVaccination(@PathVariable("id") Long vaccinationId, @Valid @RequestBody Vaccination vaccination) throws UnauthorizedAccessException, NotFoundException {
        return vaccinationService.updateVaccination(vaccinationId, vaccination);
    }

    @DeleteMapping("/vaccination/{id}")
    @Operation(
            summary = "Delete Vaccination",
            description = "Delete a specific vaccination record using its unique ID."
    )
    public ResponseEntity deleteVaccination(@PathVariable("id") Long vaccinationId) throws NotFoundException {
        return vaccinationService.deleteVaccination(vaccinationId);
    }
}
