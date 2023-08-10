package com.furguard.backend.common;

import com.furguard.backend.config.JwtService;
import com.furguard.backend.entities.User;
import com.furguard.backend.errors.NotFoundException;
import com.furguard.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonFunctions {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public Long getUserId(String token) throws NotFoundException {
        var jwt = token.substring(7);
        var email = jwtService.extractUsername(jwt);
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            return user.get().getUserId();
        }else{
            throw new NotFoundException("Profile not found");
        }
    }

    public User getUser(String token) throws NotFoundException {
        var jwt = token.substring(7);
        var email = jwtService.extractUsername(jwt);
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            return user.get();
        }else{
           throw new NotFoundException("Profile not found");
        }
    }
}
