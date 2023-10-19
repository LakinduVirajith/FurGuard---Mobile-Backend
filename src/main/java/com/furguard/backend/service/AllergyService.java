package com.furguard.backend.service;

import com.furguard.backend.dto.AllergyDTO;
import com.furguard.backend.entity.Allergy;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.exception.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AllergyService {
    AllergyDTO addAllergy(Allergy allergy) throws NotFoundException;

    List<AllergyDTO> getAllVAllergy() throws NotFoundException;

    AllergyDTO updateAllergy(Long allergyId, Allergy allergy) throws NotFoundException, UnauthorizedAccessException;

    ResponseEntity<ResponseMessage> deleteAllergy(Long allergyId) throws NotFoundException;
}
