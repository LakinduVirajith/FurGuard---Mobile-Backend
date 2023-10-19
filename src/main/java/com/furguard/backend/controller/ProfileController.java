package com.furguard.backend.controller;

import com.furguard.backend.dto.PetProfileDTO;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.service.ProfileService;
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

    @PostMapping("/profile")
    @Operation(
            summary = "Save Pet Profile",
            description = "Create a pet's profile. Provide necessary details to save the pet's information."
    )
    public PetProfileDTO saveProfile(@Valid @RequestBody PetProfile profile) throws ConflictException, NotFoundException {
        return profileService.saveProfile(profile);
    }

    @GetMapping("/profile")
    @Operation(
            summary = "Get Pet Profile",
            description = "Retrieve a pet's profile by providing the unique profile ID."
    )
    public PetProfileDTO getProfile() throws NotFoundException {
        return profileService.fetchProfileById();
    }

    @PutMapping("/profile")
    @Operation(
            summary = "Update Pet Profile",
            description = "Update a pet's profile by providing the unique profile ID and the updated details. If the profile ID exists, this operation updates the pet's profile information."
    )
    public PetProfileDTO updateProfile(@RequestBody PetProfile profile) throws NotFoundException {
        return profileService.updateProfile(profile);
    }

    @DeleteMapping("/profile")
    @Operation(
            summary = "Delete Pet Profile",
            description = "Delete a pet's profile by providing the unique profile ID. If the profile ID exists, this operation deletes the pet's profile."
    )
    public ResponseEntity<ResponseMessage> deleteProfile() throws NotFoundException {
        return profileService.deleteProfile();
    }
}
