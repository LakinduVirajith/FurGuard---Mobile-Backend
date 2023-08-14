package com.furguard.backend.common;

import com.furguard.backend.config.jwt.JwtService;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.User;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.repository.ProfileRepository;
import com.furguard.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonFunctions {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private static String token;

    public void storeJWT(String jwt) {
        token = jwt;
    }

    public String getUserEmail(){
        return jwtService.extractUsername(token);
    }

    public Long getUserId() throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(getUserEmail());

        if(user.isPresent()){
            return user.get().getUserId();
        }else{
            throw new NotFoundException("Profile not found");
        }
    }

    public User getUser() throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(getUserEmail());

        if(user.isPresent()){
            return user.get();
        }else{
            throw new NotFoundException("Profile not found");
        }
    }

    public Long getPetId() throws NotFoundException {
        Long userId = getUserId();
        Optional<PetProfile> profile = profileRepository.findByUserUserId(userId);

        if(profile.isPresent()){
            return profile.get().getPetId();
        }else{
            throw new NotFoundException("Profile not found");
        }
    }

    public PetProfile getPetProfile() throws NotFoundException {
        Long userId = getUserId();
        Optional<PetProfile> profile = profileRepository.findByUserUserId(userId);

        if(profile.isPresent()){
            return profile.get();
        }else{
            throw new NotFoundException("Profile not found");
        }
    }
}
