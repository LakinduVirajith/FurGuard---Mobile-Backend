package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.*;
import com.furguard.backend.entity.LostPetNotice;
import com.furguard.backend.entity.MedicationReminder;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.VaccinationReminder;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.repository.LostPetNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService{

    private final LostPetNoticeRepository noticeRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public List<LostPetNoticeDTO> getAllNotices() throws NotFoundException {
        List<LostPetNotice> lostPetNotices = noticeRepository.findAll();

        if(lostPetNotices.isEmpty()){
            throw new NotFoundException("no any lost pet notices found.");
        }

        List<LostPetNoticeDTO> lostPetNoticeDTOList = new ArrayList<>();
        for (LostPetNotice notice : lostPetNotices) {
            LostPetNoticeDTO noticeDTO = new LostPetNoticeDTO();
            modelMapper.map(notice, noticeDTO);
            noticeDTO.setName(notice.getPetProfile().getName());
            noticeDTO.setImage(notice.getPetProfile().getImage());
            noticeDTO.setSpecies(notice.getPetProfile().getSpecies());

            lostPetNoticeDTOList.add(noticeDTO);
        }

        return lostPetNoticeDTOList;
    }

    @Override
    public LostPetNoticeDetailsDTO getNoticeById(Long noticeId) throws NotFoundException {
        Optional<LostPetNotice> noticeOptional = noticeRepository.findById(noticeId);

        if(noticeOptional.isEmpty()){
            throw new NotFoundException("no notice found with the provided ID: " + noticeId);
        }
        LostPetNotice petNotice = noticeOptional.get();

        LostPetNoticeDetailsDTO noticeDetailsDTO = new LostPetNoticeDetailsDTO();
        modelMapper.map(petNotice, noticeDetailsDTO);
        noticeDetailsDTO.setName(petNotice.getPetProfile().getName());
        noticeDetailsDTO.setImage(petNotice.getPetProfile().getImage());
        noticeDetailsDTO.setSpecies(petNotice.getPetProfile().getSpecies());
        noticeDetailsDTO.setBreed(petNotice.getPetProfile().getBreed());
        noticeDetailsDTO.setAge(petNotice.getPetProfile().getAge());
        noticeDetailsDTO.setGender(petNotice.getPetProfile().getGender());

        return noticeDetailsDTO;
    }

    @Override
    public RemindersDTO getAllReminders() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        List<MedicationReminder> medicationReminders = profile.getMedicationReminders();
        List<VaccinationReminder> vaccinationReminders = profile.getVaccinationReminders();

        if(medicationReminders.isEmpty() && vaccinationReminders.isEmpty()){
            throw new NotFoundException("no reminders found for the pet.");
        }

        List<MedicationReminderDTO> medicationReminderDTOList = new ArrayList<>();
        List<VaccinationReminderDTO> vaccinationReminderDTOList = new ArrayList<>();

        if(!medicationReminders.isEmpty()){
            for (MedicationReminder reminder: medicationReminders){
                MedicationReminderDTO dto = new MedicationReminderDTO();
                modelMapper.map(reminder, dto);
                medicationReminderDTOList.add(dto);
            }
        }

        if(!vaccinationReminders.isEmpty()){
            for (VaccinationReminder reminder: vaccinationReminders){
                VaccinationReminderDTO dto = new VaccinationReminderDTO();
                modelMapper.map(reminder, dto);
                vaccinationReminderDTOList.add(dto);
            }
        }

        RemindersDTO remindersDTO = new RemindersDTO();
        remindersDTO.setMedicationReminders(medicationReminderDTOList);
        remindersDTO.setVaccinationReminders(vaccinationReminderDTOList);
        return remindersDTO;
    }
}
