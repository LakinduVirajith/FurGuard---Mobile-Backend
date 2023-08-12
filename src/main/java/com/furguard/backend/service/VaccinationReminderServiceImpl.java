package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.MedicationReminderDTO;
import com.furguard.backend.dto.VaccinationReminderDTO;
import com.furguard.backend.entity.*;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import com.furguard.backend.repository.VaccinationReminderRepository;
import com.furguard.backend.repository.VaccinationRepository;
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
public class VaccinationReminderServiceImpl implements VaccinationReminderService{

    private final VaccinationReminderRepository reminderRepository;
    private final VaccinationRepository vaccinationRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public VaccinationReminderDTO addVaccination(Long vaccinationId, VaccinationReminder reminder) throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        Optional<Vaccination> vaccination = vaccinationRepository.findById(vaccinationId);

        if(vaccination.isEmpty()){
            throw new NotFoundException("No vaccination records found for the given vaccination ID: " + vaccinationId);
        }

        reminder.setPetProfile(profile);
        reminder.setVaccination(vaccination.get());
        reminderRepository.save(reminder);

        return modelMapper.map(reminder, VaccinationReminderDTO.class);
    }

    @Override
    public List<VaccinationReminderDTO> fetchAllVaccinations(Long vaccinationId) throws NotFoundException, UnauthorizedAccessException {
        List<VaccinationReminder> reminders = reminderRepository.findByVaccinationVaccinationId(vaccinationId);

        if(reminders.isEmpty()){
            throw new NotFoundException("No vaccination reminders found for the given vaccination ID");
        }
        if(!reminders.get(0).getVaccination().getPetProfile().getPetId().equals(commonFunctions.getPetId())){
            throw new UnauthorizedAccessException("You are not authorized to view this contact.");
        }

        List<VaccinationReminderDTO> vaccinationReminderDTOList = new ArrayList<>();
        for (VaccinationReminder vaccinationReminder : reminders) {
            VaccinationReminderDTO vaccinationReminderDTO = modelMapper.map(vaccinationReminder, VaccinationReminderDTO.class);
            vaccinationReminderDTOList.add(vaccinationReminderDTO);
        }
        return vaccinationReminderDTOList;
    }

    @Override
    public VaccinationReminderDTO fetchVaccination(Long reminderId) throws NotFoundException, UnauthorizedAccessException {
        Optional<VaccinationReminder> reminder = reminderRepository.findById(reminderId);

        if(reminder.isEmpty()){
            throw new NotFoundException("No reminder found for the given reminder ID " + reminderId);
        }
        if(!reminder.get().getVaccination().getPetProfile().getPetId().equals(commonFunctions.getPetId())){
            throw new UnauthorizedAccessException("You are not authorized to view this contact.");
        }

        return modelMapper.map(reminder.get(), VaccinationReminderDTO.class);
    }

    @Override
    public VaccinationReminderDTO updateVaccination(Long reminderId, VaccinationReminder reminder) throws NotFoundException, UnauthorizedAccessException {
        Optional<VaccinationReminder> reminderOptional = reminderRepository.findById(reminderId);

        if(reminderOptional.isEmpty()){
            throw new NotFoundException("No reminder found for the given reminder ID " + reminderId);
        }
        if(!reminderOptional.get().getVaccination().getPetProfile().getPetId().equals(commonFunctions.getPetId())){
            throw new UnauthorizedAccessException("You are not authorized to update this contact.");
        }

        VaccinationReminder existReminder = reminderOptional.get();
        existReminder.setReminderTime(reminder.getReminderTime());
        reminderRepository.save(existReminder);

        return modelMapper.map(existReminder, VaccinationReminderDTO.class);
    }

    @Override
    public ResponseEntity removeVaccination(Long reminderId) throws NotFoundException, UnauthorizedAccessException {
        Optional<VaccinationReminder> reminder = reminderRepository.findById(reminderId);

        if(reminder.isEmpty()){
            throw new NotFoundException("No reminder found for the given reminder ID " + reminderId);
        }
        if(!reminder.get().getVaccination().getPetProfile().getPetId().equals(commonFunctions.getPetId())){
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
