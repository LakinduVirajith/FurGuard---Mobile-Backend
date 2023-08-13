package com.furguard.backend.controller;

import com.furguard.backend.dto.AllergyDTO;
import com.furguard.backend.entity.Allergy;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.service.AllergyService;
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
@Tag(name = "Allergy Controller")
public class AllergyController {

    private final AllergyService allergyService;

    @PostMapping("/allergy")
    @Operation(
            summary = "Add Pet Allergy",
            description = "Record a new allergy for your pet by providing the necessary details."
    )
    public AllergyDTO addAllergy(@Valid @RequestBody Allergy allergy) throws NotFoundException {
        return allergyService.addAllergy(allergy);
    }

    @GetMapping("/allergy")
    @Operation(
            summary = "Get All Pet Allergies",
            description = "Retrieve a list of all allergies recorded for your pet."
    )
    public List<AllergyDTO> getAllAllergy() throws NotFoundException {
        return allergyService.getAllVAllergy();
    }

    @PutMapping("/allergy/{id}")
    @Operation(
            summary = "Update Pet Allergy",
            description = "Update details of a specific allergy using its unique ID and the updated information."
    )
    public AllergyDTO updateAllergy(@PathVariable("id") Long allergyId, @Valid @RequestBody Allergy allergy) throws UnauthorizedAccessException, NotFoundException {
        return allergyService.updateAllergy(allergyId, allergy);
    }

    @DeleteMapping("/allergy/{id}")
    @Operation(
            summary = "Delete Pet Allergy",
            description = "Delete a specific allergy record using its unique ID."
    )
    public ResponseEntity deleteAllergy(@PathVariable("id") Long allergyId) throws NotFoundException {
        return allergyService.deleteAllergy(allergyId);
    }
}
