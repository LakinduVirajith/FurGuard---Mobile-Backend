package com.furguard.backend.service;

import com.furguard.backend.entity.User;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.BadRequestException;
import com.furguard.backend.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity userRegister(User user) throws ConflictException;

    ResponseEntity activate(String token) throws NotFoundException, BadRequestException;

    ResponseEntity deactivate() throws NotFoundException;
}
