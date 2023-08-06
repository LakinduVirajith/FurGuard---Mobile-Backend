package com.furguard.backend.services;

import com.furguard.backend.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface EmailService {

    String sendActivationMail(User user);
}
