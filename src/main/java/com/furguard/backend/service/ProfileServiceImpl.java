package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.config.firebase.FirebaseStorageServiceImpl;
import com.furguard.backend.dto.PetProfileDTO;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.User;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    private final FirebaseStorageServiceImpl firebaseStorageService;

    private final CommonFunctions commonFunctions;

    private final ModelMapper modelMapper;

    @Override
    public PetProfileDTO saveProfile(PetProfile profile) throws ConflictException, NotFoundException {
        User user = commonFunctions.getUser();
        Optional<PetProfile> optionalPetProfile = profileRepository.findByUserUserId(user.getUserId());

        if (optionalPetProfile.isPresent()) {
            PetProfile existingProfile = optionalPetProfile.get();

            if (existingProfile.getIsActive()) {
                throw new ConflictException("Profile already exists");
            } else {
                existingProfile.setIsActive(true);
                existingProfile.setDeactivatedDate(null);
                existingProfile.setName(profile.getName());
                existingProfile.setImage(profile.getImage());
                existingProfile.setDescription(profile.getDescription());
                existingProfile.setSpecies(profile.getSpecies());
                existingProfile.setBreed(profile.getBreed());
                existingProfile.setAge(profile.getAge());
                existingProfile.setWeight(profile.getWeight());
                existingProfile.setColor(profile.getColor());
                existingProfile.setGender(profile.getGender());

                profileRepository.save(existingProfile);
                return modelMapper.map(existingProfile, PetProfileDTO.class);
            }
        }else {
            profile.setUser(user);
            profileRepository.save(profile);
            return modelMapper.map(profile, PetProfileDTO.class);
        }
    }

    @Override
    public PetProfileDTO fetchProfileById() throws NotFoundException {
        Long userId = commonFunctions.getUserId();
        Optional<PetProfile> optionalPetProfile = profileRepository.findByUserUserId(userId);

        if (optionalPetProfile.isEmpty() || !optionalPetProfile.get().getIsActive()) {
            throw new NotFoundException("Profile not found");
        }

        return this.modelMapper.map(optionalPetProfile.get(), PetProfileDTO.class);
    }

    @Override
    public PetProfileDTO updateProfile(PetProfile profile) throws NotFoundException {
        Long userId = commonFunctions.getUserId();
        Optional<PetProfile> optionalPetProfile = profileRepository.findByUserUserId(userId);

        if (optionalPetProfile.isEmpty() || !optionalPetProfile.get().getIsActive()) {
            throw new NotFoundException("Profile not found");
        }

        PetProfile existingPetProfile = optionalPetProfile.get();

        if (!profile.getName().isEmpty()) {
            existingPetProfile.setName(profile.getName());
        }
        if (profile.getImage() != null && !profile.getImage().isEmpty()) {
            existingPetProfile.setImage(profile.getImage());
        }
        if (profile.getDescription() != null && !profile.getDescription().isEmpty()) {
            existingPetProfile.setDescription(profile.getDescription());
        }
        existingPetProfile.setSpecies(profile.getSpecies());
        if (!profile.getBreed().isEmpty()) {
            existingPetProfile.setBreed(profile.getBreed());
        }
        existingPetProfile.setAge(profile.getAge());
        if (profile.getWeight() != null && !profile.getWeight().isEmpty()) {
            existingPetProfile.setWeight(profile.getWeight());
        }
        if (profile.getColor() != null && !profile.getColor().isEmpty()) {
            existingPetProfile.setColor(profile.getColor());
        }
        existingPetProfile.setGender(profile.getGender());

        profileRepository.save(existingPetProfile);
        return this.modelMapper.map(existingPetProfile, PetProfileDTO.class);
    }

    @Override
    public ResponseEntity deleteProfile() throws NotFoundException {
        Long userId = commonFunctions.getUserId();
        Optional<PetProfile> optionalPetProfile = profileRepository.findByUserUserId(userId);

        if(optionalPetProfile.isEmpty()){
            throw new NotFoundException("Profile not found");
        }

        PetProfile existingPetProfile = optionalPetProfile.get();
        existingPetProfile.setName("");
        existingPetProfile.setImage(null);
        existingPetProfile.setDescription(null);
        existingPetProfile.setBreed("");
        existingPetProfile.setWeight(null);
        existingPetProfile.setColor(null);
        existingPetProfile.setIsActive(false);
        existingPetProfile.setDeactivatedDate(LocalDate.now());

        profileRepository.save(existingPetProfile);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("Profile with ID " + existingPetProfile.getPetId() + " has been deleted successfully");

        return ResponseEntity.ok().body(successResponse);
    }
}
