package com.furguard.backend.services;

import com.furguard.backend.entities.ResponseMessage;
import com.furguard.backend.entities.PetProfile;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    @Override
    public PetProfile postProfile(PetProfile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public PetProfile fetchProfileById(Long profileId) throws NotFoundException {
        Optional<PetProfile> optionalPetProfile = profileRepository.findById(profileId);

        if (!optionalPetProfile.isPresent()) {
            throw new NotFoundException("Profile not found");
        }

        return optionalPetProfile.get();
    }

    @Override
    public PetProfile updateProfile(Long profileId, PetProfile profile) throws NotFoundException {
        PetProfile existingPetProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("Profile not found"));

        if (profile.getName() != null && !profile.getName().isEmpty()) {
            existingPetProfile.setName(profile.getName());
        }
        if (profile.getDescription() != null && !profile.getDescription().isEmpty()) {
            existingPetProfile.setDescription(profile.getDescription());
        }
        if (profile.getSpecies() != null) {
            existingPetProfile.setSpecies(profile.getSpecies());
        }
        if (profile.getBreed() != null && !profile.getBreed().isEmpty()) {
            existingPetProfile.setBreed(profile.getBreed());
        }
        if (profile.getAge() != null) {
            existingPetProfile.setAge(profile.getAge());
        }
        if (profile.getWeight() != null && !profile.getWeight().isEmpty()) {
            existingPetProfile.setWeight(profile.getWeight());
        }
        if (profile.getColor() != null && !profile.getColor().isEmpty()) {
            existingPetProfile.setColor(profile.getColor());
        }
        if (profile.getGender() != null) {
            existingPetProfile.setGender(profile.getGender());
        }

        return profileRepository.save(existingPetProfile);
    }

    @Override
    public ResponseEntity deleteProfile(Long profileId) throws NotFoundException {
        Optional<PetProfile> optionalPetProfile = profileRepository.findById(profileId);

        if(!optionalPetProfile.isPresent()){
            throw new NotFoundException("Profile not found");
        }

        profileRepository.deleteById(profileId);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("Profile with ID " + profileId + " has been deleted successfully");

        return ResponseEntity.ok().body(successResponse);
    }
}
