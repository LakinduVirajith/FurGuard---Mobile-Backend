package com.furguard.backend.service;

import com.furguard.backend.dto.VaccinationDTO;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.Vaccination;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VaccinationService {
    VaccinationDTO addVaccination(VaccinationDTO vaccinationDTO) throws NotFoundException;

    List<VaccinationDTO> getAllVaccination() throws NotFoundException;

    VaccinationDTO getVaccination(Long vaccinationId) throws NotFoundException;

    VaccinationDTO updateVaccination(Long vaccinationId, Vaccination vaccination) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity<ResponseMessage> deleteVaccination(Long vaccinationId) throws NotFoundException;
}
