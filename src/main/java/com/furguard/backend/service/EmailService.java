package com.furguard.backend.service;

import com.furguard.backend.entity.User;

public interface EmailService {

    String sendActivationMail(User user);
}
