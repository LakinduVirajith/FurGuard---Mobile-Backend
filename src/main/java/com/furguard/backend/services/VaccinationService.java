package com.furguard.backend.services;

import com.furguard.backend.dto.VaccinationDTO;
import com.furguard.backend.entities.Vaccination;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.errors.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VaccinationService {
    VaccinationDTO addVaccination(Vaccination vaccination) throws NotFoundException;

    List<VaccinationDTO> getAllVaccination() throws NotFoundException;

    VaccinationDTO getVaccination(Long vaccinationId) throws NotFoundException;

    VaccinationDTO updateVaccination(Long vaccinationId, Vaccination vaccination) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity deleteVaccination(Long vaccinationId) throws NotFoundException;
}
