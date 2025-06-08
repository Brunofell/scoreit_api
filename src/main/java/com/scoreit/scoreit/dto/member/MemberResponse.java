package com.scoreit.scoreit.dto.member;

import java.time.LocalDate;

public record MemberResponse(Long id,
                             String name,
                             LocalDate birthDate,
                             String profileImageUrl,
                             Gender gender,
                             String bio
                             ) {
}
