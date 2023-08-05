package com.furguard.backend.services;

import com.furguard.backend.entities.PetProfile;
import com.furguard.backend.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    PetProfile postProfile(PetProfile profile);

    PetProfile fetchProfileById(Long profileId) throws NotFoundException;

    PetProfile updateProfile(Long profileId, PetProfile profile) throws NotFoundException;

    ResponseEntity deleteProfile(Long profileId) throws NotFoundException;
}
