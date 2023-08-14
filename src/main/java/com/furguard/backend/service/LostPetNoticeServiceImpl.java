package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.dto.LostPetNoticeDTO;
import com.furguard.backend.entity.LostPetNotice;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.repository.LostPetNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LostPetNoticeServiceImpl implements LostPetNoticeService{

    private final LostPetNoticeRepository noticeRepository;
    private final CommonFunctions commonFunctions;
    private final ModelMapper modelMapper;

    @Override
    public LostPetNoticeDTO addNotice(LostPetNotice lostPetNotice) throws NotFoundException, ConflictException {
        PetProfile profile = commonFunctions.getPetProfile();

        if(profile.getLostPetNotice() != null){
            throw new ConflictException("This pet already has a notice.");
        }

        lostPetNotice.setPetProfile(profile);
        return getLostPetNoticeDTO(profile, lostPetNotice);
    }

    @Override
    public LostPetNoticeDTO fetchNotice() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();

        if(profile.getLostPetNotice() == null){
            throw new NotFoundException("There is no lost pet notice for this pet.");
        }

        return getLostPetNoticeDTO(profile, profile.getLostPetNotice());
    }

    @Override
    public LostPetNoticeDTO updateNotice(LostPetNotice lostPetNotice) throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        Optional<LostPetNotice> optionalNotice = noticeRepository.findById(profile.getLostPetNotice().getLostPetNoticeId());

        if(optionalNotice.isEmpty()){
            throw new NotFoundException("There is no lost pet notice for this pet. Cannot update.");
        }

        LostPetNotice petNotice = optionalNotice.get();

        petNotice.setLostDate(lostPetNotice.getLostDate());
        if(!lostPetNotice.getLastSeenLocation().isEmpty()){
            petNotice.setLastSeenLocation(lostPetNotice.getLastSeenLocation());
        }
        if(!lostPetNotice.getMobileNumber().isEmpty()){
            petNotice.setMobileNumber(lostPetNotice.getMobileNumber());
        }
        if(lostPetNotice.getAdditionalDetails() != null){
            petNotice.setAdditionalDetails(lostPetNotice.getAdditionalDetails());
        }
        System.out.println("4");

        return getLostPetNoticeDTO(profile, petNotice);
    }

    @Override
    public ResponseEntity setAsFound() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        Optional<LostPetNotice> noticeOptional = noticeRepository.findById(profile.getLostPetNotice().getLostPetNoticeId());

        if(noticeOptional.isEmpty()){
            throw new NotFoundException("There is no lost pet notice for this pet.");
        }

        LostPetNotice petNotice = noticeOptional.get();
        petNotice.setIsFound(true);
        petNotice.setFoundDate(LocalDate.now());
        noticeRepository.save(petNotice);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("Congratulations! Your lost pet has been found. The notice has been marked as found and will be removed within 7 days.");

        return ResponseEntity.ok().body(successResponse);
    }

    @Override
    public ResponseEntity setAsNotFound() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        Optional<LostPetNotice> noticeOptional = noticeRepository.findById(profile.getLostPetNotice().getLostPetNoticeId());

        if(noticeOptional.isEmpty()){
            throw new NotFoundException("There is no lost pet notice for this pet.");
        }

        LostPetNotice petNotice = noticeOptional.get();
        petNotice.setIsFound(false);
        petNotice.setFoundDate(null);
        noticeRepository.save(petNotice);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("Your lost pet has not been found. The notice has been marked as not-found.");

        return ResponseEntity.ok().body(successResponse);
    }

    @Override
    public ResponseEntity deleteNotice() throws NotFoundException {
        PetProfile profile = commonFunctions.getPetProfile();
        Optional<LostPetNotice> noticeOptional = noticeRepository.findById(profile.getLostPetNotice().getLostPetNoticeId());

        if(noticeOptional.isEmpty()){
            throw new NotFoundException("There is no lost pet notice for this pet.");
        }

        LostPetNotice petNotice = noticeOptional.get();

        petNotice.getPetProfile().setLostPetNotice(null);
        noticeRepository.delete(petNotice);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("Your lost pet has been found and the notice has been removed.");

        return ResponseEntity.ok().body(successResponse);
    }

    private LostPetNoticeDTO getLostPetNoticeDTO(PetProfile profile, LostPetNotice petNotice) {
        noticeRepository.save(petNotice);

        LostPetNoticeDTO noticeDTO = new LostPetNoticeDTO();
        modelMapper.map(petNotice, noticeDTO);
        noticeDTO.setName(profile.getName());
        noticeDTO.setImage(profile.getImage());
        noticeDTO.setSpecies(profile.getSpecies());
        return noticeDTO;
    }
}
