package com.furguard.backend.services;

import com.furguard.backend.dto.EmergencyContactDTO;
import com.furguard.backend.entities.EmergencyContact;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.errors.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmergencyContactService {
    ResponseEntity addContact(String token, EmergencyContact contact) throws NotFoundException;

    List<EmergencyContactDTO> fetchAllContact(String token) throws NotFoundException;

    EmergencyContactDTO updateContactById(String token, Long contactId, EmergencyContact contact) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity deleteById(String token, Long contactId) throws NotFoundException, UnauthorizedAccessException;
}
