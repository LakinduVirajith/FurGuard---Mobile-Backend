package com.furguard.backend.service;

import com.furguard.backend.dto.PetProfileDTO;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    PetProfileDTO saveProfile(PetProfile profile) throws ConflictException, NotFoundException;

    PetProfileDTO fetchProfileById() throws NotFoundException;

    PetProfileDTO updateProfile(PetProfile profile) throws NotFoundException;

    ResponseEntity deleteProfile() throws NotFoundException;
}
