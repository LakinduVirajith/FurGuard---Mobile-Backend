package com.furguard.backend.service;

import com.furguard.backend.dto.LostPetNoticeDTO;
import com.furguard.backend.dto.LostPetNoticeDetailsDTO;
import com.furguard.backend.dto.RemindersDTO;
import com.furguard.backend.exception.NotFoundException;

import java.util.List;

public interface HomeService {
    List<LostPetNoticeDTO> getAllNotices() throws NotFoundException;

    LostPetNoticeDetailsDTO getNoticeById(Long noticeId) throws NotFoundException;

    RemindersDTO getAllReminders() throws NotFoundException;
}
