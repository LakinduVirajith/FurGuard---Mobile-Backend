package com.furguard.backend.services;

import com.furguard.backend.dto.PetProfileDTO;
import com.furguard.backend.entities.PetProfile;
import com.furguard.backend.errors.AlreadyExistException;
import com.furguard.backend.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    PetProfileDTO saveProfile(String token, PetProfile profile) throws AlreadyExistException, NotFoundException;

    PetProfileDTO fetchProfileById(String token) throws NotFoundException;

    PetProfileDTO updateProfile(String token, PetProfile profile) throws NotFoundException;

    ResponseEntity deleteProfile(String token) throws NotFoundException;
}
