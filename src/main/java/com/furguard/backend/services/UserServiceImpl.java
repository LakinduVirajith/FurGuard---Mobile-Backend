package com.furguard.backend.services;

import com.furguard.backend.entities.ErrorMessage;
import com.furguard.backend.entities.User;
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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User saveRegister(User user) {
        // Encode Password using passwordEncoder
        String encodedPassword = encodePassword(user.getPassword());
        user.setPassword(encodedPassword);

        // Generate activation token and set its expiry date
        // Activation link valid for 10 mints
        String activationToken = UUID.randomUUID().toString();
        LocalDateTime activationTokenExpiry = LocalDateTime.now().plusMinutes(10);
        user.setActivationToken(activationToken);
        user.setActivationTokenExpiry(activationTokenExpiry);

        return userRepository.save(user);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public ResponseEntity activate(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new NotFoundException("User Not Found");
        }

        user.get().setIsActive(true);
        userRepository.save(user.get());

        ErrorMessage successResponse = new ErrorMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("User activated successfully");

        return ResponseEntity.ok().body(successResponse);
    }

    @Override
    public ResponseEntity deactivate(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new NotFoundException("User Not Found");
        }

        user.get().setIsActive(false);
        userRepository.save(user.get());

        ErrorMessage successResponse = new ErrorMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("User deactivated successfully");

        return ResponseEntity.ok().body(successResponse);
    }
}
