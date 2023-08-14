package com.furguard.backend.service;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.User;
import com.furguard.backend.exception.ConflictException;
import com.furguard.backend.exception.BadRequestException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final CommonFunctions commonFunctions;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity userRegister(User user) throws ConflictException {
        Optional<User> emailCondition = userRepository.findByEmail(user.getEmail());
        if(emailCondition.isPresent()){
            throw new ConflictException("Email already exists");
        }

        // Encode Password using passwordEncoder
        String encodedPassword = encodePassword(user.getPassword());
        user.setPassword(encodedPassword);

        // Generate activation token and set its expiry date
        String activationToken = UUID.randomUUID().toString().substring(0, 32);

        // Activation link valid for 10 mints
        LocalDateTime activationTokenExpiry = LocalDateTime.now().plusMinutes(10);
        user.setActivationToken(activationToken);
        user.setActivationTokenExpiry(activationTokenExpiry);

        // Email service call
        emailService.sendActivationMail(user);

        userRepository.save(user);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("User registered successfully");

        return ResponseEntity.ok().body(successResponse);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public ResponseEntity activate(String token) throws NotFoundException, BadRequestException {
        User user = userRepository.findByActivationToken(token)
                .orElseThrow(() -> new NotFoundException("User not found"));

        LocalDateTime activationTokenExpiry = user.getActivationTokenExpiry();
        if (activationTokenExpiry.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Activation token has expired");
        }

        user.setIsActive(true);
        userRepository.save(user);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("User activated successfully");

        return ResponseEntity.ok().body(successResponse);
    }

    @Override
    public ResponseEntity deactivate() throws NotFoundException {
        User user = commonFunctions.getUser();

        if(user == null){
            throw new NotFoundException("User not found");
        }

        user.setIsActive(false);
        userRepository.save(user);

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("User deactivated successfully");

        return ResponseEntity.ok().body(successResponse);
    }
}
