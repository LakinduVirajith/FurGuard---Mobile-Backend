package com.furguard.backend.services;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.VaccinationDTO;
import com.furguard.backend.entities.PetProfile;
import com.furguard.backend.entities.ResponseMessage;
import com.furguard.backend.entities.Vaccination;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.errors.UnauthorizedAccessException;
import com.furguard.backend.repositories.VaccinationRepository;
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
public class VaccinationServiceImpl implements VaccinationService{

    private final VaccinationRepository vaccinationRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public VaccinationDTO addVaccination(Vaccination vaccination) throws NotFoundException {
        PetProfile profileOperation = commonFunctions.getPetProfile();

        vaccination.setPetProfile(profileOperation);
        vaccinationRepository.save(vaccination);

        return modelMapper.map(vaccination, VaccinationDTO.class);
    }

    @Override
    public List<VaccinationDTO> getAllVaccination() throws NotFoundException {
        Long pet_id = commonFunctions.getPetId();

        List<Vaccination> vaccinations = vaccinationRepository.findAllByPetProfilePetId(pet_id);
        if(vaccinations.isEmpty()){
            throw new NotFoundException("No vaccination records found for the current user.");
        }

        List<VaccinationDTO> vaccinationDTOList = new ArrayList<>();
        for (Vaccination vaccination : vaccinations) {
            VaccinationDTO vaccinationDTO = modelMapper.map(vaccination, VaccinationDTO.class);
            vaccinationDTOList.add(vaccinationDTO);
        }

        return vaccinationDTOList;
    }

    @Override
    public VaccinationDTO getVaccination(Long vaccinationId) throws NotFoundException {
        Optional<Vaccination> vaccination = vaccinationRepository.findById(vaccinationId);

        if(vaccination.isEmpty()){
            throw new NotFoundException("The requested vaccination details could not be found for the ID: " + vaccinationId);
        }

        return modelMapper.map(vaccination.get(), VaccinationDTO.class);
    }

    @Override
    public VaccinationDTO updateVaccination(Long vaccinationId, Vaccination vaccination) throws NotFoundException, UnauthorizedAccessException {
        Vaccination existVaccination = vaccinationRepository.findById(vaccinationId)
                .orElseThrow(() -> new NotFoundException("The requested vaccination details could not be found for the ID: " + vaccinationId));

        Long petId = commonFunctions.getPetId();
        if(!existVaccination.getPetProfile().getPetId().equals(petId)){
            throw new UnauthorizedAccessException("Unauthorized: You do not have permission to update this vaccination.");
        }

        if(!vaccination.getName().isEmpty()){
            existVaccination.setName(vaccination.getName());
        }
        existVaccination.setAdministeredDate(vaccination.getAdministeredDate());
        existVaccination.setExpirationDate(vaccination.getExpirationDate());

        vaccinationRepository.save(existVaccination);
        return modelMapper.map(existVaccination, VaccinationDTO.class);
    }

    @Override
    public ResponseEntity deleteVaccination(Long vaccinationId) throws NotFoundException {
        Optional<Vaccination> vaccination = vaccinationRepository.findById(vaccinationId);

        if(vaccination.isEmpty()){
            throw new NotFoundException("The requested vaccination record was not found for the ID: " + vaccinationId);
        }

        vaccinationRepository.deleteById(vaccinationId);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("The vaccination record has been successfully deleted.");

        return ResponseEntity.ok().body(successResponse);
    }
}
