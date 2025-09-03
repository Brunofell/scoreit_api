package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.service.EmailResetService;
import com.scoreit.scoreit.service.NotificationEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Fluxo de alteração de e-mail:
 *  - POST /api/change-email -> gera token e envia link para o novo e-mail
 *  - POST /api/reset-email  -> aplica a troca usando o token
 */
@RestController
@RequestMapping("/api")
public class ResetEmailController {

    @Autowired
    private EmailResetService emailResetService;

    @Autowired
    private NotificationEmailService notificationEmailService;

    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestParam String email) {
        // Gera o token e tenta enviar o e-mail
        String token = emailResetService.createEmailResetToken(email);
        try {
            notificationEmailService.sendEmailChangeOrThrow(email, token);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Não foi possível enviar o e-mail de verificação para alteração.");
        }
        return ResponseEntity.ok("Um e-mail de verificação foi enviado para o novo endereço.");
    }

    @PostMapping("/reset-email")
    public ResponseEntity<String> resetEmail(@RequestParam String token, @RequestParam String newEmail) {
        if (!emailResetService.isTokenValid(token)) {
            return ResponseEntity.badRequest().body("Token inválido ou expirado.");
        }
        emailResetService.updateEmail(token, newEmail);
        return ResponseEntity.ok("E-mail atualizado com sucesso.");
    }
}
