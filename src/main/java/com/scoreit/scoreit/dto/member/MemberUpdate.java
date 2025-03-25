package com.scoreit.scoreit.dto.member;


public record MemberUpdate(
        Long id,
        String name,
        String email,
        String password,
        String bio
) {
}
