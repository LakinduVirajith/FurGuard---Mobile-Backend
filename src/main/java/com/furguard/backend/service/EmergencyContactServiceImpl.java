package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.EmergencyContactDTO;
import com.furguard.backend.entity.EmergencyContact;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.User;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.repository.EmergencyContactRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergencyContactServiceImpl implements EmergencyContactService{

    private final EmergencyContactRepository contactRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ResponseMessage> addContact(EmergencyContact contact) throws NotFoundException {
        User user = commonFunctions.getUser();

        contact.setUser(user);
        contactRepository.save(contact);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("Emergency contact number has been successfully listed.");

        return ResponseEntity.ok().body(successResponse);
    }

    @Override
    public List<EmergencyContactDTO> fetchAllContact() throws NotFoundException {
        Long userId = commonFunctions.getUserId();
        List<EmergencyContact> emergencyContacts = contactRepository.findAllByUserUserId(userId);

        if(emergencyContacts.isEmpty()){
            throw new NotFoundException("No emergency contacts found for the current user.");
        }

        List<EmergencyContactDTO> emergencyContactDTOList = new ArrayList<>();
        for (EmergencyContact emergencyContact : emergencyContacts) {
            EmergencyContactDTO emergencyContactDTO = modelMapper.map(emergencyContact, EmergencyContactDTO.class);
            emergencyContactDTOList.add(emergencyContactDTO);
        }

        return emergencyContactDTOList;
    }

    @Override
    public EmergencyContactDTO updateContactById(Long contactId, EmergencyContact contact) throws NotFoundException, UnauthorizedAccessException {
        EmergencyContact existContact = contactRepository.findById(contactId)
                .orElseThrow(() -> new NotFoundException("Emergency contact not found for the given ID: " + contactId));

        Long userId = commonFunctions.getUserId();
        if(!existContact.getUser().getUserId().equals(userId)){
            throw new UnauthorizedAccessException("You are not authorized to update this contact.");
        }

        if (!existContact.getName().isEmpty()) {
            existContact.setName(contact.getName());
        }
        if (!existContact.getMobileNumber().isEmpty()) {
            existContact.setMobileNumber(contact.getMobileNumber());
        }
        contactRepository.save(existContact);

        return modelMapper.map(existContact, EmergencyContactDTO.class);
    }

    @Override
    public ResponseEntity<ResponseMessage> deleteById(Long contactId) throws NotFoundException, UnauthorizedAccessException {
        EmergencyContact existContact = contactRepository.findById(contactId)
                .orElseThrow(() -> new NotFoundException("The requested emergency contact could not be found."));

        Long userId = commonFunctions.getUserId();
        if(!existContact.getUser().getUserId().equals(userId)){
            throw new UnauthorizedAccessException("You are not authorized to update this contact.");
        }

        contactRepository.deleteById(existContact.getEmergencyContactId());

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("The emergency contact has been successfully deleted.");

        return ResponseEntity.ok().body(successResponse);
    }
}
