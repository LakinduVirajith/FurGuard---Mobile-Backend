package com.furguard.backend.controllers;

import com.furguard.backend.entities.User;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User userRegister(@Valid @RequestBody User user){
        return userService.saveRegister(user);
    }

    @PutMapping("/user/activate/{id}")
    public ResponseEntity userActivate(@PathVariable("id") Long Id) throws NotFoundException {
        return userService.activate(Id);
    }

    @PutMapping("/user/deactivate/{id}")
    public ResponseEntity userDeactivate(@PathVariable("id") Long Id) throws NotFoundException {
        return userService.deactivate(Id);
    }
}
