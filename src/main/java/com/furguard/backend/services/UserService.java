package com.furguard.backend.services;

import com.furguard.backend.entities.User;
import com.furguard.backend.errors.AlreadyExistException;
import com.furguard.backend.errors.BadRequestException;
import com.furguard.backend.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity userRegister(User user) throws AlreadyExistException;

    ResponseEntity activate(String token) throws NotFoundException, BadRequestException;

    ResponseEntity deactivate() throws NotFoundException;
}
