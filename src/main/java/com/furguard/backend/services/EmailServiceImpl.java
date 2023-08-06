package com.furguard.backend.services;

import com.furguard.backend.entities.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String fromMail;

    @Value(("${base.url}"))
    private String baseUrl;

    @Override
    public String sendActivationMail(User user) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(fromMail);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Activate Your Account");

            String activationUrl = baseUrl + "/user/activate?token=" + user.getActivationToken();

            String emailBody = "<html>"
                    + "<body style='font-family: Arial, sans-serif;'>"
                    + "<h3>Hello " + user.getFullName() + ",</h3>"
                    + "<p>Thank you for registering with FurGuard. To activate your account, please click the link below:</p>"
                    + "<p><a href='" + activationUrl + "'>Activate My Account</a></p>"
                    + "<p>This activation link will expire in 10 minutes for security reasons, so make sure to activate your account promptly.</p>"
                    + "<p>If you didn't sign up for FurGuard, you can safely ignore this email.</p>"
                    + "<p>Best regards,<br>The FurGuard Team</p>"
                    + "</body>"
                    + "</html>";

            messageHelper.setText(emailBody, true);

            mailSender.send(mimeMessage);

            return "Mail send successfully";

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
