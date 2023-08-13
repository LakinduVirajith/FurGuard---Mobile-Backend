package com.furguard.backend.service;

import com.furguard.backend.dto.EmergencyContactDTO;
import com.furguard.backend.entity.EmergencyContact;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmergencyContactService {
    ResponseEntity addContact(EmergencyContact contact) throws NotFoundException;

    List<EmergencyContactDTO> fetchAllContact() throws NotFoundException;

    EmergencyContactDTO updateContactById(Long contactId, EmergencyContact contact) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity deleteById(Long contactId) throws NotFoundException, UnauthorizedAccessException;
}
