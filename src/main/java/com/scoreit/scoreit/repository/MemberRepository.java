package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface MemberRepository extends JpaRepository<Member, Long> {
    UserDetails findByEmail(String email);
}
