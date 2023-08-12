package com.furguard.backend.service;

import com.furguard.backend.dto.MedicationDTO;
import com.furguard.backend.entity.Medication;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicationService {
    MedicationDTO addMedication(Medication medication) throws NotFoundException;

    List<MedicationDTO> getAllMedication() throws NotFoundException;

    MedicationDTO getMedication(Long medicationId) throws NotFoundException;

    MedicationDTO updateMedication(Long medicationId, Medication medication) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity deleteMedication(Long medicationId) throws NotFoundException;
}
