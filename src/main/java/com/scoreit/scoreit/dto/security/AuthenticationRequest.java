package com.scoreit.scoreit.dto.security;

public record AuthenticationRequest(
        String email,
        String password
) {
}
