package com.scoreit.scoreit.dto.member;

import com.scoreit.scoreit.entity.Member;

public record MemberRegister(
        String name,
        String email,
        String password
) {
    public Member toModel() {
        return new Member(name, email, password);
    }
}