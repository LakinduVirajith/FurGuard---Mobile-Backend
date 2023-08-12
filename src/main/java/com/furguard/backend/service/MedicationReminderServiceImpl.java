package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.MedicationReminderDTO;
import com.furguard.backend.entity.Medication;
import com.furguard.backend.entity.MedicationReminder;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.repository.MedicationReminderRepository;
import com.furguard.backend.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicationReminderServiceImpl implements MedicationReminderService{

    private final MedicationReminderRepository reminderRepository;
    private final MedicationRepository medicationRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public MedicationReminderDTO addReminder(Long medicationId, MedicationReminder reminder) throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        Optional<Medication> medication = medicationRepository.findById(medicationId);

        if(medication.isEmpty()){
            throw new NotFoundException("No medication records found for the given medication ID: " + medicationId);
        }

        reminder.setPetProfile(profile);
        reminder.setMedication(medication.get());
        reminder.setFrequency(medication.get().getFrequency());
        reminderRepository.save(reminder);

        return modelMapper.map(reminder, MedicationReminderDTO.class);
    }

    @Override
    public List<MedicationReminderDTO> fetchAllReminders(Long medicationId) throws NotFoundException, UnauthorizedAccessException {
        List<MedicationReminder> reminders = reminderRepository.findByMedicationMedicationId(medicationId);

        if(reminders.isEmpty()){
            throw new NotFoundException("No medication reminders found for the given medication ID");
        }
        if(!reminders.get(0).getMedication().getPetProfile().getPetId().equals(commonFunctions.getPetId())){
            throw new UnauthorizedAccessException("You are not authorized to view this contact.");
        }

        List<MedicationReminderDTO> medicationReminderDTOList = new ArrayList<>();
        for (MedicationReminder medicationReminder : reminders) {
            MedicationReminderDTO medicationReminderDTO = modelMapper.map(medicationReminder, MedicationReminderDTO.class);
            medicationReminderDTOList.add(medicationReminderDTO);
        }
        return medicationReminderDTOList;
    }

    @Override
    public MedicationReminderDTO fetchReminder(Long reminderId) throws NotFoundException, UnauthorizedAccessException {
        Optional<MedicationReminder> reminder = reminderRepository.findById(reminderId);

        if(reminder.isEmpty()){
            throw new NotFoundException("No reminder found for the given reminder ID " + reminderId);
        }
        if(!reminder.get().getMedication().getPetProfile().getPetId().equals(commonFunctions.getPetId())){
            throw new UnauthorizedAccessException("You are not authorized to view this contact.");
        }

        return modelMapper.map(reminder.get(), MedicationReminderDTO.class);
    }

    @Override
    public MedicationReminderDTO updateReminder(Long reminderId, MedicationReminder reminder) throws NotFoundException, UnauthorizedAccessException {
        Optional<MedicationReminder> reminderOptional = reminderRepository.findById(reminderId);

        if(reminderOptional.isEmpty()){
            throw new NotFoundException("No reminder found for the given reminder ID " + reminderId);
        }
        if(!reminderOptional.get().getMedication().getPetProfile().getPetId().equals(commonFunctions.getPetId())){
            throw new UnauthorizedAccessException("You are not authorized to update this contact.");
        }

        MedicationReminder existReminder = reminderOptional.get();
        existReminder.setReminderTime(reminder.getReminderTime());
        reminderRepository.save(existReminder);

        return modelMapper.map(existReminder, MedicationReminderDTO.class);
    }

    @Override
    public ResponseEntity removeReminder(Long reminderId) throws NotFoundException, UnauthorizedAccessException {
        Optional<MedicationReminder> reminder = reminderRepository.findById(reminderId);

        if(reminder.isEmpty()){
            throw new NotFoundException("No reminder found for the given reminder ID " + reminderId);
        }
        if(!reminder.get().getMedication().getPetProfile().getPetId().equals(commonFunctions.getPetId())){
            throw new UnauthorizedAccessException("You are not authorized to delete this contact.");
        }

        reminderRepository.deleteById(reminderId);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("The reminder has been successfully removed.");

        return ResponseEntity.ok().body(successResponse);
    }
}
