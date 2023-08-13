package com.furguard.backend.service;

import com.furguard.backend.dto.MedicationReminderDTO;
import com.furguard.backend.entity.MedicationReminder;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicationReminderService {
    MedicationReminderDTO addReminder(Long medicationId, MedicationReminder reminder) throws NotFoundException;

    List<MedicationReminderDTO> fetchAllReminders(Long medicationId) throws NotFoundException, UnauthorizedAccessException;

    MedicationReminderDTO fetchReminder(Long reminderId) throws NotFoundException, UnauthorizedAccessException;

    MedicationReminderDTO updateReminder(Long reminderId, MedicationReminder reminder) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity removeReminder(Long reminderId) throws NotFoundException, UnauthorizedAccessException;
}
