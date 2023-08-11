package com.furguard.backend.auth;

import com.furguard.backend.exception.BadRequestException;
import com.furguard.backend.exception.InvalidUserException;
import com.furguard.backend.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request) throws InvalidUserException;

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, IOException, BadRequestException;

    ResponseEntity logout(String token) throws BadRequestException;
}
