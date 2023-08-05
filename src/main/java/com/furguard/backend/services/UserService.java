package com.furguard.backend.services;

import com.furguard.backend.entities.User;
import com.furguard.backend.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User saveRegister(User user);

    ResponseEntity activate(Long id) throws NotFoundException;

    ResponseEntity deactivate(Long id) throws NotFoundException;
}
