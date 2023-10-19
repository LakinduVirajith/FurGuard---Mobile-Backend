package com.furguard.backend.service;

import com.furguard.backend.dto.LostPetNoticeDTO;
import com.furguard.backend.entity.LostPetNotice;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface LostPetNoticeService {
    LostPetNoticeDTO addNotice(LostPetNotice lostPetNotice) throws NotFoundException, ConflictException;

    LostPetNoticeDTO fetchNotice() throws NotFoundException;

    LostPetNoticeDTO updateNotice(LostPetNotice lostPetNotice) throws NotFoundException;

    ResponseEntity<ResponseMessage> setAsFound() throws NotFoundException;

    ResponseEntity<ResponseMessage> setAsNotFound() throws NotFoundException;

    ResponseEntity<ResponseMessage> deleteNotice() throws NotFoundException;
}
