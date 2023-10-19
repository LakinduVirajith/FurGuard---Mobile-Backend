package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.SafeAreaDTO;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.SafeArea;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.repository.SafeAreaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SafeAreaServiceImpl implements SafeAreaService{

    private final SafeAreaRepository safeAreaRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public SafeAreaDTO setSafeArea(SafeArea safeArea) throws NotFoundException, ConflictException {
        PetProfile profile = commonFunctions.getPetProfile();

        if(profile.getSafeArea() != null){
            throw new ConflictException("This pet already has a safe area set. You can update the existing safe area.");
        }

        safeArea.setPetProfile(profile);
        safeAreaRepository.save(safeArea);

        return modelMapper.map(safeArea, SafeAreaDTO.class);
    }

    @Override
    public SafeAreaDTO getSafeArea() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        SafeArea safeArea = profile.getSafeArea();

        if(safeArea == null){
            throw new NotFoundException("No safe area found for this pet.");
        }

        return modelMapper.map(safeArea, SafeAreaDTO.class);
    }

    @Override
    public SafeAreaDTO updateSafeArea(SafeArea safeArea) throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        SafeArea existSafeArea = profile.getSafeArea();

        if(existSafeArea == null){
            throw new NotFoundException("No safe area found for this pet.");
        }

        existSafeArea.setCenterLatitude(safeArea.getCenterLatitude());
        existSafeArea.setCenterLongitude(safeArea.getCenterLongitude());
        existSafeArea.setRadius(safeArea.getRadius());
        safeAreaRepository.save(existSafeArea);

        return modelMapper.map(existSafeArea, SafeAreaDTO.class);
    }

    @Override
    public ResponseEntity<ResponseMessage> removeSafeArea() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        SafeArea safeArea = profile.getSafeArea();

        if(safeArea == null){
            throw new NotFoundException("No safe area found for this pet.");
        }

        safeArea.setPetProfile(null);
        safeAreaRepository.delete(safeArea);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("The safe area has been successfully removed.");

        return ResponseEntity.ok().body(successResponse);
    }
}
