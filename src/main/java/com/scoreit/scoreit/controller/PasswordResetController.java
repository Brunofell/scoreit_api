package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.service.NotificationEmailService;
import com.scoreit.scoreit.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Fluxo de redefinição de senha:
 *  - POST /api/forgot-password -> gera token e envia link de redefinição
 *  - POST /api/reset-password  -> aplica a nova senha usando o token
 */
@RestController
@RequestMapping("/api")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private NotificationEmailService notificationEmailService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        String token = passwordResetService.createPasswordResetToken(email);
        try {
            notificationEmailService.sendPasswordResetOrThrow(email, token);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Não foi possível enviar o e-mail de redefinição.");
        }
        return ResponseEntity.ok("E-mail de redefinição enviado com sucesso.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (!passwordResetService.isTokenValid(token)) {
            return ResponseEntity.badRequest().body("Token inválido ou expirado.");
        }
        passwordResetService.updatePassword(token, newPassword);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}
