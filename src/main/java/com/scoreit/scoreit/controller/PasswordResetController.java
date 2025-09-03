package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private PasswordResetEmailService passwordResetEmailService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email){
        String token = passwordResetService.createPasswordResetToken(email);
        passwordResetEmailService.sendPasswordResetEmail(email, token);
        return ResponseEntity.ok("Password reset e-mail was sended.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword){
        if(!passwordResetService.isTokenValid(token)){
            return ResponseEntity.badRequest().body("Invalid or Expired Token");
        }
        passwordResetService.updatePassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }

}
