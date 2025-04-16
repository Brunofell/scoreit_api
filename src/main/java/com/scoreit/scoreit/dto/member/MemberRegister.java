package com.scoreit.scoreit.dto.member;

import com.scoreit.scoreit.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MemberRegister(
        String name,
        String email,
        LocalDate birthDate,
        Gender gender,
        String password
) {
    public Member toModel() {
        return new Member(name, email, birthDate, gender, password);
    }
}