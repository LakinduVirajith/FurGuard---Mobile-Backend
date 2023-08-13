package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.AllergyDTO;
import com.furguard.backend.entity.Allergy;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.repository.AllergyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AllergyServiceImpl implements AllergyService{

    private final AllergyRepository allergyRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public AllergyDTO addAllergy(Allergy allergy) throws NotFoundException {
        PetProfile profileOperation = commonFunctions.getPetProfile();

        allergy.setPetProfile(profileOperation);
        allergyRepository.save(allergy);

        return modelMapper.map(allergy, AllergyDTO.class);
    }

    @Override
    public List<AllergyDTO> getAllVAllergy() throws NotFoundException {
        Long pet_id = commonFunctions.getPetId();

        List<Allergy> allergies = allergyRepository.findAllByPetProfilePetId(pet_id);
        if(allergies.isEmpty()){
            throw new NotFoundException("No allergy records found for the current user.");
        }

        List<AllergyDTO> allergyDTOList = new ArrayList<>();
        for (Allergy allergy : allergies) {
            AllergyDTO allergyDTO = modelMapper.map(allergy, AllergyDTO.class);
            allergyDTOList.add(allergyDTO);
        }

        return allergyDTOList;
    }

    @Override
    public AllergyDTO updateAllergy(Long allergyId, Allergy allergy) throws NotFoundException, UnauthorizedAccessException {
        Allergy existAllergy = allergyRepository.findById(allergyId)
                .orElseThrow(() -> new NotFoundException("The requested allergy details could not be found for the ID: " + allergyId));

        Long petId = commonFunctions.getPetId();
        if(!existAllergy.getPetProfile().getPetId().equals(petId)){
            throw new UnauthorizedAccessException("Unauthorized: You do not have permission to update this allergy.");
        }

        if(!allergy.getDescription().isEmpty()){
            existAllergy.setDescription(allergy.getDescription());
        }

        allergyRepository.save(existAllergy);
        return modelMapper.map(existAllergy, AllergyDTO.class);
    }

    @Override
    public ResponseEntity deleteAllergy(Long allergyId) throws NotFoundException {
        Optional<Allergy> allergy = allergyRepository.findById(allergyId);

        if(allergy.isEmpty()){
            throw new NotFoundException("The requested allergy details was not found for the ID: " + allergyId);
        }

        allergyRepository.deleteById(allergyId);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("The allergy details has been successfully deleted.");

        return ResponseEntity.ok().body(successResponse);
    }
}
