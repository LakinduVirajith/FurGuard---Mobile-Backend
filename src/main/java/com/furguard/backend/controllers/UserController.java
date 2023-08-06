package com.furguard.backend.controllers;

import com.furguard.backend.entities.User;
import com.furguard.backend.errors.AlreadyExistEmailException;
import com.furguard.backend.errors.InvalidTokenException;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity userRegister(@Valid @RequestBody User user) throws AlreadyExistEmailException {
        return userService.userRegister(user);
    }

    @GetMapping("/activate")
    public ResponseEntity userActivate(@RequestParam("token") String token) throws NotFoundException, InvalidTokenException {
        return userService.activate(token);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity userDeactivate(@PathVariable("id") Long Id) throws NotFoundException {
        return userService.deactivate(Id);
    }
}
