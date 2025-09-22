package com.scoreit.scoreit.dto.member;

import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String name,
        String handle,
        LocalDate birthDate,
        String profileImageUrl,
        com.scoreit.scoreit.dto.member.Gender gender,
        String bio
) {}
