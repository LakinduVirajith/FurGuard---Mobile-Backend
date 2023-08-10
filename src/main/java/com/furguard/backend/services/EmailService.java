package com.furguard.backend.services;

import com.furguard.backend.entities.User;

public interface EmailService {

    String sendActivationMail(User user);
}
