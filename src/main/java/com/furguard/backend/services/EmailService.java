package com.furguard.backend.services;

import com.furguard.backend.entities.User;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendActivationEmail(User user) {
        // Construct the activation link using the activation token
        String activationLink = "https://fur-guard.com/activate?token=" + user.getActivationToken();

        // Send the activation email to the user's email address
        String subject = "Activate Your Account";
        String body = "Hello " + user.getFullName() + ",\n\n"
                + "Thank you for registering with our website. To activate your account, please click the link below:\n"
                + activationLink + "\n\n"
                + "This activation link will expire in 24 hours.\n\n"
                + "Best regards,\n"
                + "Your Website Team";

//        YOUR_EMAIL_SERVICE_PROVIDER.sendEmail(user.getEmail(), subject, body);
    }
}
