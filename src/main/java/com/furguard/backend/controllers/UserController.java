package com.furguard.backend.controllers;

import com.furguard.backend.entities.AuthenticationRequest;
import com.furguard.backend.entities.AuthenticationResponse;
import com.furguard.backend.entities.User;
import com.furguard.backend.errors.AlreadyExistEmailException;
import com.furguard.backend.errors.InvalidTokenException;
import com.furguard.backend.errors.InvalidUserException;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.services.AuthenticationService;
import com.furguard.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity userRegister(@Valid @RequestBody User user) throws AlreadyExistEmailException {
        return userService.userRegister(user);
    }

    @GetMapping("/activate")
    public ResponseEntity userActivate(@RequestParam("token") String token) throws NotFoundException, InvalidTokenException {
        return userService.activate(token);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws InvalidUserException {
        return ResponseEntity.ok().body(authenticationService.authenticate(request));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity userDeactivate(@PathVariable("id") Long Id) throws NotFoundException {
        return userService.deactivate(Id);
    }
}
