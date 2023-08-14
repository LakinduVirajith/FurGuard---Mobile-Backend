package com.furguard.backend.service;

import com.furguard.backend.dto.SafeAreaDTO;
import com.furguard.backend.entity.SafeArea;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface SafeAreaService {

    SafeAreaDTO setSafeArea(SafeArea safeArea) throws NotFoundException, ConflictException;

    SafeAreaDTO getSafeArea() throws NotFoundException;

    SafeAreaDTO updateSafeArea(SafeArea safeArea) throws NotFoundException;

    ResponseEntity removeSafeArea() throws NotFoundException;
}
