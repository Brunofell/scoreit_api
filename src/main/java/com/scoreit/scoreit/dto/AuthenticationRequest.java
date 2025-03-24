package com.scoreit.scoreit.dto;

public record AuthenticationRequest(
        String email,
        String password
) {
}
