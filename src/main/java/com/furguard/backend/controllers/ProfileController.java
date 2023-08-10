package com.furguard.backend.controllers;

import com.furguard.backend.dto.PetProfileDTO;
import com.furguard.backend.entities.PetProfile;
import com.furguard.backend.errors.AlreadyExistException;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.services.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/furry")
@RequiredArgsConstructor
@Tag(name = "Pet Profile Controller")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(
            summary = "Save Pet Profile",
            description = "Create a pet's profile. Provide necessary details to save the pet's information."
    )
    @PostMapping("/profile")
    public PetProfileDTO saveProfile(@RequestHeader("Authorization") String token, @Valid @RequestBody PetProfile profile) throws AlreadyExistException, NotFoundException {
        return profileService.saveProfile(token, profile);
    }

    @Operation(
            summary = "Get Pet Profile",
            description = "Retrieve a pet's profile by providing the unique profile ID."
    )
    @GetMapping("/profile")
    public PetProfileDTO getProfile(@RequestHeader("Authorization") String token) throws NotFoundException {
        return profileService.fetchProfileById(token);
    }

    @Operation(
            summary = "Update Pet Profile",
            description = "Update a pet's profile by providing the unique profile ID and the updated details. If the profile ID exists, this operation updates the pet's profile information."
    )
    @PutMapping("/profile")
    public PetProfileDTO updateProfile(@RequestHeader("Authorization") String token, @RequestBody PetProfile profile) throws NotFoundException {
        return profileService.updateProfile(token, profile);
    }

    @Operation(
            summary = "Delete Pet Profile",
            description = "Delete a pet's profile by providing the unique profile ID. If the profile ID exists, this operation deletes the pet's profile."
    )
    @DeleteMapping("/profile")
    public ResponseEntity deleteProfile(@RequestHeader("Authorization") String token) throws NotFoundException {
        return profileService.deleteProfile(token);
    }
}
