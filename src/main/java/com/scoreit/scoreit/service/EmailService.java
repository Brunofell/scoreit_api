package com.scoreit.scoreit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendPasswordResetEmail(String to, String token){
        //String resetLink = "http://localhost:8080/api/reset-password?token=" + token;

        String resetLink = "http://localhost:3000/nova_senha?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);

        message.setTo(to);
        message.setSubject("Redefinição de Senha - ScoreIT");
        message.setText("Clique no link para redefinir sua senha: " + resetLink);
        mailSender.send(message);
    }
}
