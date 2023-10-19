package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.MedicationDTO;
import com.furguard.backend.entity.Medication;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.repository.MedicationRepository;
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
public class MedicationServiceImpl implements MedicationService{

    private final MedicationRepository medicationRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public MedicationDTO addMedication(Medication medication) throws NotFoundException {
        PetProfile profileOperation = commonFunctions.getPetProfile();

        medication.setPetProfile(profileOperation);
        medicationRepository.save(medication);

        return modelMapper.map(medication, MedicationDTO.class);
    }

    @Override
    public List<MedicationDTO> getAllMedication() throws NotFoundException {
        Long pet_id = commonFunctions.getPetId();

        List<Medication> medications = medicationRepository.findAllByPetProfilePetId(pet_id);
        if(medications.isEmpty()){
            throw new NotFoundException("No medication records found for the current user.");
        }

        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        for (Medication medication : medications) {
            MedicationDTO medicationDTO = modelMapper.map(medication, MedicationDTO.class);
            medicationDTOList.add(medicationDTO);
        }

        return medicationDTOList;
    }

    @Override
    public MedicationDTO getMedication(Long medicationId) throws NotFoundException {
        Optional<Medication> medication = medicationRepository.findById(medicationId);

        if(medication.isEmpty()){
            throw new NotFoundException("The requested medication details could not be found for the ID: " + medicationId);
        }

        return modelMapper.map(medication.get(), MedicationDTO.class);
    }

    @Override
    public MedicationDTO updateMedication(Long medicationId, Medication medication) throws NotFoundException, UnauthorizedAccessException {
        Medication existMedication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new NotFoundException("The requested medication details could not be found for the ID: " + medicationId));

        Long petId = commonFunctions.getPetId();
        if(!existMedication.getPetProfile().getPetId().equals(petId)){
            throw new UnauthorizedAccessException("Unauthorized: You do not have permission to update this medication.");
        }

        if(!medication.getName().isEmpty()){
            existMedication.setName(medication.getName());
        }
        existMedication.setDosage(medication.getDosage());
        if(!medication.getFrequency().isEmpty()){
            existMedication.setFrequency(medication.getFrequency());
        }
        existMedication.setStartDate(medication.getStartDate());
        if(medication.getEndDate() != null){
            existMedication.setEndDate(medication.getEndDate());
        }

        medicationRepository.save(existMedication);
        return modelMapper.map(existMedication, MedicationDTO.class);
    }

    @Override
    public ResponseEntity<ResponseMessage> deleteMedication(Long medicationId) throws NotFoundException {
        Optional<Medication> medication = medicationRepository.findById(medicationId);

        if(medication.isEmpty()){
            throw new NotFoundException("The requested medication record was not found for the ID: " + medicationId);
        }

        medicationRepository.deleteById(medicationId);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("The medication record has been successfully deleted.");

        return ResponseEntity.ok().body(successResponse);
    }
}
