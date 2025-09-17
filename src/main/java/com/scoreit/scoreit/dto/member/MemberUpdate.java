package com.scoreit.scoreit.dto.member;


import java.time.LocalDate;

public record MemberUpdate(
        Long id,
        String name,
        String handle,
        LocalDate birthDate,
        Gender gender,
        String password,
        String bio
) {
}
