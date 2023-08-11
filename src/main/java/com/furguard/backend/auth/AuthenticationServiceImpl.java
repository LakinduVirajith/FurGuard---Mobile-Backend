package com.furguard.backend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furguard.backend.config.JwtService;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.entity.Token;
import com.furguard.backend.entity.User;
import com.furguard.backend.enums.TokenType;
import com.furguard.backend.exception.BadRequestException;
import com.furguard.backend.exception.InvalidUserException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.repository.TokenRepository;
import com.furguard.backend.repository.UserRepository;
import com.furguard.backend.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
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
        saveToken(user, jwtToken);

        var refreshToken = jwtService.generateRefreshToken(user);
        
        return AuthenticationResponse.builder()
                .statusCode(200).
                status(HttpStatus.OK).
                message("User authenticated successfully").
                accessToken(jwtToken).
                refreshToken(refreshToken).build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, IOException, BadRequestException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if(optionalUser.isPresent() && !optionalUser.get().getIsActive()){
            throw new BadRequestException("Account was deactivated");
        }

        if(userEmail != null){
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            if(jwtService.isTokenValid(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);
                saveToken(user, accessToken);

                var authResponse = AuthenticationResponse.builder().
                        statusCode(200).
                        status(HttpStatus.OK).
                        message("Using refresh token user authenticated successfully").
                        accessToken(accessToken).
                        refreshToken(refreshToken).build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public ResponseEntity logout(String token) throws BadRequestException {
        var jwt = token.substring(7);
        Optional<Token> optionalToken = tokenRepository.findByToken(jwt);

        if(optionalToken.isPresent()){
            var existToken = optionalToken.get();
            existToken.setExpired(true);
            existToken.setRevoked(true);
            tokenRepository.save(existToken);
        }else{
            throw new BadRequestException("Invalid logout");
        }

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("User logout successfully");

        return ResponseEntity.ok().body(successResponse);
    }

    private void saveToken(User user, String jwtToken) {
        Optional<Token> OptionalToken = tokenRepository.findByUserUserId(user.getUserId());

        Token token;
        if(OptionalToken.isPresent()){
            token = OptionalToken.get();
            token.setToken(jwtToken);
            token.setExpired(false);
            token.setRevoked(false);
        }else{
            token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false).build();
        }
        tokenRepository.save(token);
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
