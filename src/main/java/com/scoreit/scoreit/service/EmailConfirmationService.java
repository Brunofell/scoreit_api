package com.scoreit.scoreit.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class EmailConfirmationService {

    @Autowired
    private JavaMailSender mailSender;

    // URL pública do frontend (sem / no final), ex: https://scoreit.vercel.app
    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    // Locale padrão para compor a rota [locale]/confirma_conta
    @Value("${app.frontend.default-locale:pt}")
    private String defaultLocale;

    public void sendVerificationEmail(String to, String token) {
        String subject = "Confirmação de E-mail - ScoreIt";

        String encoded = URLEncoder.encode(token, StandardCharsets.UTF_8);
        // https://seu-front/{locale}/confirma_conta?token=...
        String confirmLink = String.format("%s/%s/confirma_conta?token=%s",
                trimTrailingSlash(frontendBaseUrl), defaultLocale, encoded);

        String message = "<p>Olá! Clique no link abaixo para confirmar seu e-mail:</p>"
                + "<p><a href=\"" + confirmLink + "\">Confirmar E-mail</a></p>"
                + "<p>Se você não solicitou este cadastro, ignore esta mensagem.</p>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom("scoreit2025@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }

    private String trimTrailingSlash(String url) {
        if (url == null) return "";
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
