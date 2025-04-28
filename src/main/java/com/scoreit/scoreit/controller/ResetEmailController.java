package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.service.EmailResetService;
import com.scoreit.scoreit.service.PasswordResetService;
import com.scoreit.scoreit.service.EmailResetEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResetEmailController {
    @Autowired
    private EmailResetService emailResetService;
    @Autowired
    private EmailResetEmailService emailResetEmailService;


    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestParam String email){
        String token = emailResetService.createEmailResetToken(email);
        emailResetEmailService.sendResetEmail(email, token);
        return ResponseEntity.ok("A verification email has been sent to your new email address.");
    }

    @PostMapping("/reset-email")
    public ResponseEntity<String> resetEmail(@RequestParam String token, @RequestParam String newEmail){
        if(!emailResetService.isTokenValid(token)){
            return ResponseEntity.badRequest().body("Invalid or Expired Token");
        }
        emailResetService.updateEmail(token, newEmail);
        return ResponseEntity.ok("Email reset successfully");
    }
}
