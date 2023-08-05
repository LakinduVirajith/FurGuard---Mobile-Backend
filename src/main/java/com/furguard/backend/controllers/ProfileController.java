package com.furguard.backend.controllers;

import com.furguard.backend.entities.PetProfile;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/profile")
    public PetProfile saveProfile(@Valid @RequestBody PetProfile profile){
        return profileService.postProfile(profile);
    }

    @GetMapping("/profile/{id}")
    public PetProfile getProfile(@PathVariable("id") Long profileId) throws NotFoundException {
        return profileService.fetchProfileById(profileId);
    }

    @PutMapping("/profile/{id}")
    public PetProfile updateProfile(@PathVariable("id") Long profileId, @RequestBody PetProfile profile) throws NotFoundException {
        return profileService.updateProfile(profileId, profile);
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity deleteProfile(@PathVariable("id") Long profileId) throws NotFoundException {
        return profileService.deleteProfile(profileId);
    }
}
