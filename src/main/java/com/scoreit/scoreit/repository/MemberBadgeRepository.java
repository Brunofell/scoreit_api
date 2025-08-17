package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.Badge;
import com.scoreit.scoreit.entity.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {
    boolean existsByMemberAndBadge(Member member, Badge badge);
    List<MemberBadge> findByMember(Member member);
}