package com.furguard.backend.services;

import com.furguard.backend.entities.AuthenticationRequest;
import com.furguard.backend.entities.AuthenticationResponse;
import com.furguard.backend.errors.InvalidUserException;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request) throws InvalidUserException;
}
