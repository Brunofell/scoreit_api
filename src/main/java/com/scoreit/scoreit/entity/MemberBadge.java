package com.scoreit.scoreit.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "member_badges")
public class MemberBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Badge badge;

    private LocalDateTime dataConquista;

    public MemberBadge() {}

    public MemberBadge(Member member, Badge badge) {
        this.member = member;
        this.badge = badge;
        this.dataConquista = LocalDateTime.now();
    }

    public LocalDateTime getDataConquista() {
        return dataConquista;
    }

    public void setDataConquista(LocalDateTime dataConquista) {
        this.dataConquista = dataConquista;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

