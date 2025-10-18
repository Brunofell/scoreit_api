package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
    Member findByHandle(String handle);

    Optional<Member> findOptionalByEmail(String email);

    List<Member> findByHandleContainingIgnoreCase(String handle);
    List<Member> findByEnabled(boolean enabled);
}
