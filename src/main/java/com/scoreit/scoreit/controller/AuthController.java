package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            try {
                token = token.substring(7);
                tokenService.validateToken(token);
                return ResponseEntity.ok().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
