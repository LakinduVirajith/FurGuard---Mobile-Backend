package com.furguard.backend.service;

import com.furguard.backend.dto.UserDTO;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.BadRequestException;
import com.furguard.backend.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ResponseMessage> userRegister(UserDTO userDTO) throws ConflictException;

    ResponseEntity<ResponseMessage> activate(String token) throws NotFoundException, BadRequestException;

    ResponseEntity<ResponseMessage> deactivate() throws NotFoundException;
}
