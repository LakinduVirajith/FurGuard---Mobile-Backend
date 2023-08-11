package com.furguard.backend.controllers;

import com.furguard.backend.auth.AuthenticationRequest;
import com.furguard.backend.auth.AuthenticationResponse;
import com.furguard.backend.auth.AuthenticationService;
import com.furguard.backend.entities.User;
import com.furguard.backend.errors.AlreadyExistException;
import com.furguard.backend.errors.BadRequestException;
import com.furguard.backend.errors.InvalidUserException;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "User Registration",
            description = "Register a new user. Provide necessary details to create a user account."
    )
    public ResponseEntity userRegister(@Valid @RequestBody User user) throws AlreadyExistException {
        return userService.userRegister(user);
    }

    @GetMapping("/activate")
    @Operation(
            summary = "Activate User Account",
            description = "Activate a user account using an activation token received via email."
    )
    public ResponseEntity userActivate(@RequestParam("token") String token) throws NotFoundException, BadRequestException {
        return userService.activate(token);
    }

    @PostMapping("/authenticate")
    @Operation(
            summary = "User Authentication",
            description = "Authenticate a user by providing valid credentials."
    )
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws InvalidUserException {
        return ResponseEntity.ok().body(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @Operation(
            summary = "Refresh Access Token",
            description = "Refresh the access token by providing a valid refresh token. This endpoint allows you to obtain a new access token using a valid refresh token, which helps in maintaining user authentication without requiring the user to log in again."
    )
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, IOException, BadRequestException {
        authenticationService.refreshToken(request, response);
    }

    @PutMapping("/deactivate")
    @Operation(
            summary = "Deactivate Account",
            description = "Deactivate a user account by providing the unique user ID."
    )
    public ResponseEntity userDeactivate(@RequestHeader("Authorization") String token) throws NotFoundException {
        return userService.deactivate(token);
    }

    @PutMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Invalidate the user's authentication token to log out."
    )
    public ResponseEntity logout(@RequestHeader("Authorization") String token) throws BadRequestException {
        return authenticationService.logout(token);
    }
}
