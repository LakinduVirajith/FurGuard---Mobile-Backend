package com.furguard.backend.services;

import com.furguard.backend.entities.User;
import com.furguard.backend.errors.AlreadyExistEmailException;
import com.furguard.backend.errors.InvalidTokenException;
import com.furguard.backend.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity userRegister(User user) throws AlreadyExistEmailException;

    ResponseEntity activate(String token) throws NotFoundException, InvalidTokenException;

    ResponseEntity deactivate(Long id) throws NotFoundException;
}
