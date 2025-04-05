package com.scoreit.scoreit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token){
        String resetLink = "http://localhost:8080/api/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Redefinição de Senha - ScoreIT");
        message.setText("Clique no link para redefinir sua senha: " + resetLink);
        mailSender.send(message);
    }
}
