package com.furguard.backend.service;

import com.furguard.backend.dto.LostPetNoticeDTO;
import com.furguard.backend.entity.LostPetNotice;
import com.furguard.backend.exception.AlreadyExistException;
import com.furguard.backend.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface LostPetNoticeService {
    LostPetNoticeDTO addNotice(LostPetNotice lostPetNotice) throws NotFoundException, AlreadyExistException;

    LostPetNoticeDTO fetchNotice() throws NotFoundException;

    LostPetNoticeDTO updateNotice(LostPetNotice lostPetNotice) throws NotFoundException;

    ResponseEntity setAsFound() throws NotFoundException;

    ResponseEntity setAsNotFound() throws NotFoundException;

    ResponseEntity deleteNotice() throws NotFoundException;
}
