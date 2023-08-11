package com.furguard.backend.services;

import com.furguard.backend.dto.EmergencyContactDTO;
import com.furguard.backend.entities.EmergencyContact;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.errors.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmergencyContactService {
    ResponseEntity addContact(EmergencyContact contact) throws NotFoundException;

    List<EmergencyContactDTO> fetchAllContact() throws NotFoundException;

    EmergencyContactDTO updateContactById(Long contactId, EmergencyContact contact) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity deleteById(Long contactId) throws NotFoundException, UnauthorizedAccessException;
}
