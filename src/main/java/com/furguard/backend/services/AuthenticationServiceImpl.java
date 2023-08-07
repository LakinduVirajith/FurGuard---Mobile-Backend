package com.furguard.backend.services;

import com.furguard.backend.config.JwtService;
import com.furguard.backend.entities.AuthenticationRequest;
import com.furguard.backend.entities.AuthenticationResponse;
import com.furguard.backend.entities.User;
import com.furguard.backend.errors.InvalidUserException;
import com.furguard.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws InvalidUserException {
        Optional<User> userCondition = userRepository.findByEmail(request.getEmail());
        if(userCondition.isPresent() && !userCondition.get().getIsActive()){
            regenerateActivationToken(userCondition.get());
            throw new InvalidUserException("Your account is not activated. Please check your email to verify your account first.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().statusCode(200).status(HttpStatus.OK).message("User authenticated successfully").token(jwtToken).build();
    }

    private void regenerateActivationToken(User user) {
        // Generate activation token and set its expiry date
        String activationToken = UUID.randomUUID().toString().substring(0, 32);

        // Activation link valid for 10 mints
        LocalDateTime activationTokenExpiry = LocalDateTime.now().plusMinutes(10);
        user.setActivationToken(activationToken);
        user.setActivationTokenExpiry(activationTokenExpiry);

        // Email service call
        emailService.sendActivationMail(user);

        userRepository.save(user);
    }
}
