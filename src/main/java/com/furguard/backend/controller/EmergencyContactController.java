package com.furguard.backend.controller;

import com.furguard.backend.dto.EmergencyContactDTO;
import com.furguard.backend.entity.EmergencyContact;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.service.EmergencyContactService;
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
    public ResponseEntity<ResponseMessage> addContact(@Valid @RequestBody EmergencyContact contact) throws NotFoundException {
        return contactService.addContact(contact);
    }

    @GetMapping("/contact")
    @Operation(
            summary = "Fetch All Emergency Contacts",
            description = "Retrieve a list of all emergency contacts associated with the user."
    )
    public List<EmergencyContactDTO> fetchAllContact() throws NotFoundException {
        return contactService.fetchAllContact();
    }

    @PutMapping("/contact/{id}")
    @Operation(
            summary = "Update Emergency Contact",
            description = "Update an existing emergency contact. Provide necessary details to update the contact."
    )
    public EmergencyContactDTO updateContact(@PathVariable("id") Long contactId, @Valid @RequestBody EmergencyContact contact) throws NotFoundException, UnauthorizedAccessException {
        return contactService.updateContactById(contactId, contact);
    }

    @DeleteMapping("/contact/{id}")
    @Operation(
            summary = "Delete Emergency Contact",
            description = "Delete an existing emergency contact by ID."
    )
    public ResponseEntity<ResponseMessage> deleteContact(@PathVariable("id") Long contactId) throws NotFoundException, UnauthorizedAccessException {
        return  contactService.deleteById(contactId);
    }
}
