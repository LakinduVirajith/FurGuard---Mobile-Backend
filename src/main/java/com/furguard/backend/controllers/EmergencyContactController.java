package com.furguard.backend.controllers;

import com.furguard.backend.dto.EmergencyContactDTO;
import com.furguard.backend.entities.EmergencyContact;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.errors.UnauthorizedAccessException;
import com.furguard.backend.services.EmergencyContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emergency")
@RequiredArgsConstructor
@Tag(name = "Emergency Contact Controller")
public class EmergencyContactController {

    private final EmergencyContactService contactService;

    @PostMapping("/contact")
    @Operation(
            summary = "Add Emergency Contact",
            description = "Add a new emergency contact. Provide necessary details to create the contact."
    )
    public ResponseEntity addContact(@RequestHeader("Authorization") String token, @Valid @RequestBody EmergencyContact contact) throws NotFoundException {
        return contactService.addContact(token, contact);
    }

    @GetMapping("/contact")
    @Operation(
            summary = "Fetch All Emergency Contacts",
            description = "Retrieve a list of all emergency contacts associated with the user."
    )
    public List<EmergencyContactDTO> fetchAllContact(@RequestHeader("Authorization") String token) throws NotFoundException {
        return contactService.fetchAllContact(token);
    }

    @PutMapping("/contact/{id}")
    @Operation(
            summary = "Update Emergency Contact",
            description = "Update an existing emergency contact. Provide necessary details to update the contact."
    )
    public EmergencyContactDTO updateContact(@RequestHeader("Authorization") String token, @PathVariable("id") Long contactId, @Valid @RequestBody EmergencyContact contact) throws NotFoundException, UnauthorizedAccessException {
        return contactService.updateContactById(token, contactId, contact);
    }

    @DeleteMapping("/contact/{id}")
    @Operation(
            summary = "Delete Emergency Contact",
            description = "Delete an existing emergency contact by ID."
    )
    public ResponseEntity deleteContact(@RequestHeader("Authorization") String token, @PathVariable("id") Long contactId) throws NotFoundException, UnauthorizedAccessException {
        return  contactService.deleteById(token, contactId);
    }
}
