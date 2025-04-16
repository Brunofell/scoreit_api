package com.scoreit.scoreit.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmailResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne
    private Member member;
    private LocalDateTime expireDate;

    public EmailResetToken(Long id, String token, Member member, LocalDateTime expireDate) {
        this.id = id;
        this.token = token;
        this.member = member;
        this.expireDate = expireDate;
    }

    public EmailResetToken() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}
