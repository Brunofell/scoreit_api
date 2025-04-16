package com.scoreit.scoreit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailResetEmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendResetEmail(String to, String token){
        String resetLink = "http://localhost:8080/api/reset-email?token=" + token;

        // String resetLink = "http://localhost:3000/nova_senha?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);

        message.setTo(to);
        message.setSubject("Redefinição de E-mail - ScoreIT");
        message.setText("Clique no link para redefinir sua email: " + resetLink);
        mailSender.send(message);
    }
}
