package com.furguard.backend.services;

import com.furguard.backend.entities.ResponseMessage;
import com.furguard.backend.entities.User;
import com.furguard.backend.errors.AlreadyExistEmailException;
import com.furguard.backend.errors.InvalidTokenException;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity userRegister(User user) throws AlreadyExistEmailException {
        User emailCondition = userRepository.findByEmailIgnoreCase(user.getEmail());
        if(emailCondition != null){
            throw new AlreadyExistEmailException("Email already exists");
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
    public ResponseEntity activate(String token) throws NotFoundException, InvalidTokenException {
        User user = userRepository.findByActivationToken(token);

        if(user == null){
            throw new NotFoundException("User not found");
        }

        LocalDateTime activationTokenExpiry = user.getActivationTokenExpiry();
        if (activationTokenExpiry.isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Activation token has expired");
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
    public ResponseEntity deactivate(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new NotFoundException("User not found");
        }

        user.get().setIsActive(false);
        userRepository.save(user.get());

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("User deactivated successfully");

        return ResponseEntity.ok().body(successResponse);
    }
}