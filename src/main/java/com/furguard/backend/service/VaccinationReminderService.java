package com.furguard.backend.service;

import com.furguard.backend.dto.VaccinationReminderDTO;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.VaccinationReminder;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VaccinationReminderService {
    VaccinationReminderDTO addVaccination(Long vaccinationId, VaccinationReminder reminder) throws NotFoundException;

    List<VaccinationReminderDTO> fetchAllVaccinations(Long vaccinationId) throws NotFoundException, UnauthorizedAccessException;

    VaccinationReminderDTO fetchVaccination(Long reminderId) throws NotFoundException, UnauthorizedAccessException;

    VaccinationReminderDTO updateVaccination(Long reminderId, VaccinationReminder reminder) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity<ResponseMessage> removeVaccination(Long reminderId) throws NotFoundException, UnauthorizedAccessException;
}
